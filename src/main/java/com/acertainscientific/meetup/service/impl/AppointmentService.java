package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.AppointmentAddDto;
import com.acertainscientific.meetup.mapper.AppointmentMapper;
import com.acertainscientific.meetup.mapper.RoomMapper;
import com.acertainscientific.meetup.model.AppointmentModel;
import com.acertainscientific.meetup.service.IAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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


}
