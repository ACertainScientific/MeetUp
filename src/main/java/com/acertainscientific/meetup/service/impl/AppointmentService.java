package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.dto.AppointmentAddDto;
import com.acertainscientific.meetup.mapper.AppointmentMapper;
import com.acertainscientific.meetup.model.AppointmentModel;
import com.acertainscientific.meetup.service.IAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService extends ServiceImpl<AppointmentMapper, AppointmentModel> implements IAppointmentService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean addAppointmentService(AppointmentAddDto appointmentAddDto) {
        AppointmentModel appointmentModel = modelMapper.map(appointmentAddDto, AppointmentModel.class);
        if (true == false){
            return false;
        }

        this.save(appointmentModel);
        return true;
    }

    @Override
    public boolean deleteAppointmentService(Integer id){
        return this.removeById(id);
    }
}
