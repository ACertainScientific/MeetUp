package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.AppointmentAddDto;
import com.acertainscientific.meetup.dto.DetailAppointmentDto;
import com.acertainscientific.meetup.dto.PageResponseDto;
import com.acertainscientific.meetup.mapper.AppointmentMapper;
import com.acertainscientific.meetup.mapper.RoomMapper;
import com.acertainscientific.meetup.model.AppointmentModel;
import com.acertainscientific.meetup.service.IAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService extends ServiceImpl<AppointmentMapper, AppointmentModel> implements IAppointmentService {

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean addAppointmentService(AppointmentAddDto appointmentAddDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AppointmentModel appointmentModel = modelMapper.map(appointmentAddDto, AppointmentModel.class);
        if (appointmentMapper.appointmentsGivenTime(appointmentModel.getStartTime(), appointmentModel.getEndTime(),
                appointmentModel.getRoomId(), appointmentModel.getDate()) > 0 ||
        roomMapper.searchAllByRoomId(appointmentModel.getRoomId()) == 0) {
            return false;
        }

        this.save(appointmentModel);
        return true;
    }

    @Override
    public boolean deleteAppointmentService(Integer id){
        AppointmentModel appointmentModel = this.getById(id);
        if (appointmentModel != null){
            appointmentModel.setIsDeleted(1);
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

    @Override
    public boolean appointmentDecision(Integer id){
        AppointmentModel appointmentModel = this.getById(id);
        return appointmentModel != null && appointmentModel.getIsDeleted() != 1;
    }

    @Override
    public DetailAppointmentDto detailAppointment(Integer id){
        AppointmentModel appointmentModel = this.getById(id);
        DetailAppointmentDto detailAppointmentDto = new DetailAppointmentDto();
        detailAppointmentDto.setRoomId(appointmentModel.getRoomId());
        detailAppointmentDto.setUserId(appointmentModel.getUserId());
        detailAppointmentDto.setStartTime(appointmentModel.getStartTime());
        detailAppointmentDto.setEndTime(appointmentModel.getEndTime());
        detailAppointmentDto.setDate(appointmentModel.getDate());
        detailAppointmentDto.setMonth(appointmentModel.getMonth());
        detailAppointmentDto.setYear(appointmentModel.getYear());
        return detailAppointmentDto;
    }





}
