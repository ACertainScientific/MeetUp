package com.acertainscientific.meetup.service;

import com.acertainscientific.meetup.dto.AppointmentAddDto;
import com.acertainscientific.meetup.model.AppointmentModel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IAppointmentService extends IService<AppointmentModel> {

    boolean addAppointmentService(AppointmentAddDto appointmentAddDto);

    boolean deleteAppointmentService(Integer id);
}
