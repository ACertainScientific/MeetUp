package com.acertainscientific.meetup.service;

import com.acertainscientific.meetup.dto.AppointmentAddDto;
import com.acertainscientific.meetup.dto.DetailAppointmentDto;
import com.acertainscientific.meetup.dto.PageResponseDto;
import com.acertainscientific.meetup.model.AppointmentModel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IAppointmentService extends IService<AppointmentModel> {

    boolean addAppointmentService(AppointmentAddDto appointmentAddDto);

    boolean deleteAppointmentService(Integer id);

//    PageResponseDto listAppointment(Integer page, Integer pageSize, Integer roomId, Integer startTime, Integer endTime,
//                                    String userId, Integer month, Integer year, Integer date);

    public boolean appointmentDecision(Integer id);

    public DetailAppointmentDto detailAppointment(Integer id);
}
