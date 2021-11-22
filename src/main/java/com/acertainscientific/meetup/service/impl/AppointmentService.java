package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.*;
import com.acertainscientific.meetup.mapper.AppointmentMapper;
import com.acertainscientific.meetup.mapper.RoomMapper;
import com.acertainscientific.meetup.model.AppointmentModel;
import com.acertainscientific.meetup.service.IAppointmentService;
import com.acertainscientific.meetup.util.RedisUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class AppointmentService extends ServiceImpl<AppointmentMapper, AppointmentModel> implements IAppointmentService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private ModelMapper modelMapper;

    public boolean isValidTime(Integer date, Integer month, Integer year ,Integer startTime, Integer endTime){
        Calendar now = Calendar.getInstance();
        Integer now_year = now.get(Calendar.YEAR);
        Integer now_month = now.get(Calendar.MONTH);
        Integer now_day = now.get(Calendar.DATE);
        Integer now_hour = now.get(Calendar.HOUR);
        Integer now_minute = now.get(Calendar.MINUTE);
        Integer now_second = now.get(Calendar.SECOND);
        Integer current_second = (now_hour * 60 * 60) + (now_minute * 60) + now_second;

        Integer max_second = (24 * 60 * 60);
        if (startTime >= endTime) {return false;}

        if (startTime >= max_second || endTime >= max_second) return false;

        if (startTime < 0 ) return false;

        if (ValidTimeHelper(date, month, now_year)) return false;

        if (now_year > year){
            return false;
        }
        if (now_year.equals(year)){
            if (now_month > month) {
                return false;
            }
            if (now_month.equals(month)){
                if (now_day > date){
                    return false;
                }
                if (now_day.equals(date)){
                    return current_second <= startTime;
                }
            }
        }

        return true;


    }


    public boolean isValidTimeForCertainDate(Integer date, Integer month, Integer year){

        if (ValidTimeHelper(date, month, year)) return false;

        return true;


    }

    private boolean ValidTimeHelper(Integer date, Integer month, Integer year) {
        if (month < 1 || month > 12) return true;
        if (date < 1) return true;

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            if (date > 31) return true;
        }else{
            if (month == 2){
                if (year % 4 == 0){
                    if (date > 29) return true;
                }else{
                    if (date > 28) return true;
                }
            }else{
                if (date > 30) return true;
            }
        }
        return false;
    }

    //added the edge to check whether the user is valid.
    @Override
    public boolean updateAppointmentService(AppointmentUpdateDto appointmentUpdateDto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentModel appointmentModel = modelMapper.map(appointmentUpdateDto, AppointmentModel.class);
        Integer appointmentId = appointmentModel.getId();
        AppointmentModel appointmentModel1 = this.getById(appointmentId);
        if (appointmentMapper.isValidUser(appointmentModel.getUserId()) == 0){
            return false;
        }
        if (appointmentModel1 != null && appointmentModel1.getIsDeleted() != 1 &&isValidTime(appointmentModel.getDate(), appointmentModel.getMonth(),
                appointmentModel.getYear(), appointmentModel.getStartTime(), appointmentModel.getEndTime()) &&
        appointmentMapper.appointmentsWithoutSelfTime(appointmentModel.getStartTime(),
                appointmentModel.getEndTime(), appointmentModel.getRoomId(),
                appointmentModel.getDate(), appointmentModel.getYear(), appointmentModel.getMonth(),
                appointmentId) == 0){
            appointmentModel.setUpdatedAt((int)(System.currentTimeMillis()/1000));
            this.updateById(appointmentModel);
            if (redisUtil.hasKey("Appointment:" + appointmentId)){
                redisUtil.del("Appointment:" + appointmentId);
            }
            return true;
        }

        return false;
    }


    @Override
    public boolean addAppointmentService(AppointmentAddDto appointmentAddDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentModel appointmentModel = modelMapper.map(appointmentAddDto, AppointmentModel.class);

        if (appointmentMapper.isValidUser(appointmentModel.getUserId()) == 0 || !isValidTime(appointmentModel.getDate(), appointmentModel.getMonth(), appointmentModel.getYear(),
                appointmentModel.getStartTime(), appointmentModel.getEndTime()) || appointmentMapper.appointmentsGivenTime(appointmentModel.getStartTime(), appointmentModel.getEndTime(),
                appointmentModel.getRoomId(), appointmentModel.getDate(), appointmentModel.getYear(), appointmentModel.getMonth()) > 0 ||
        roomMapper.searchAllByRoomId(appointmentModel.getRoomId()) == 0
        ) {
            return false;
        }
        appointmentModel.setCreatedAt((int)(System.currentTimeMillis()/1000));
        this.save(appointmentModel);
        return true;
    }

    // added redis delete if the redis contains the row which need to delete
    @Override
    public boolean deleteAppointmentService(Integer id){
        AppointmentModel appointmentModel = this.getById(id);
        if (appointmentModel != null){
            appointmentModel.setIsDeleted(1);
            appointmentModel.setDeletedAt((int)(System.currentTimeMillis()/1000));
            this.updateById(appointmentModel);
            if (redisUtil.hasKey("Appointment:" + id)){
                redisUtil.del("Appointment:" + id);
            }
            return true;
        }
        return false;
    }

//    @Override
//    public PageResponseDto listAppointment(Integer page, Integer pageSize, Integer roomId, Integer startTime, Integer endTime,
//                                           String userId, Integer month, Integer year, Integer date){
//        PageHelper.startPage(page,pageSize);
//
//    }

    // check if the appointment is valid to operate. If the appointment is deleted or NUll, no operation can be done.
    @Override
    public boolean appointmentDecision(Integer id){
        if (redisUtil.hasKey("Appointment:" + id)) return true;
        AppointmentModel appointmentModel = this.getById(id);
        if (appointmentModel != null && appointmentModel.getIsDeleted() != 1){
            return true;
        }
        return false;
    }
    //* return all the appointment attributes based on the unique id(all for a row)
    @Override
    public DetailAppointmentDto detailAppointment(Integer id){
        if (redisUtil.hasKey("Appointment:" + id)){
            return modelMapper.map(redisUtil.get("Appointment:" + id), DetailAppointmentDto.class);
        }
        AppointmentModel appointmentModel = this.getById(id);
        DetailAppointmentDto detailAppointmentDto = new DetailAppointmentDto();
        detailAppointmentDto.setRoomId(appointmentModel.getRoomId());
        detailAppointmentDto.setUserId(appointmentModel.getUserId());
        detailAppointmentDto.setStartTime(appointmentModel.getStartTime());
        detailAppointmentDto.setEndTime(appointmentModel.getEndTime());
        detailAppointmentDto.setDate(appointmentModel.getDate());
        detailAppointmentDto.setMonth(appointmentModel.getMonth());
        detailAppointmentDto.setYear(appointmentModel.getYear());
        redisUtil.set("Appointment:"+id, detailAppointmentDto);
        return detailAppointmentDto;
    }

    // return all the appointment from the table
    @Override
    public PageResponseDto listAppointment(Integer page, Integer pageSize, Integer roomId, Integer startTime, Integer endTime,
                                    String userId,Integer fromMonth, Integer fromYear,Integer fromDate,
                                           Integer toMonth, Integer toYear, Integer toDate){
        if (!isValidTimeForCertainDate(fromDate, fromMonth, fromYear) || !isValidTimeForCertainDate(toDate, toMonth, toYear))
        PageHelper.startPage(page,pageSize);
        List<AppointmentModel> list = appointmentMapper.listAppointment(roomId,startTime,endTime,userId,fromMonth,fromYear,fromDate,
                toMonth, toYear, toDate);
        PageInfo pageInfo = new PageInfo(list);
        List<AppointmentListDto> listDtos = modelMapper.map(list, new TypeToken<List<AppointmentListDto>>(){}.getType());
        PageResponseDto pageResponseDto= new PageResponseDto();
        pageResponseDto.setList(listDtos);
        pageResponseDto.setTotalCount((int) pageInfo.getTotal());
        pageResponseDto.setPageCount(pageInfo.getPages());
        pageResponseDto.setPerPage(pageInfo.getPageSize());
        pageResponseDto.setCurrentPage(pageInfo.getPageNum());
        return pageResponseDto;
    }





}
