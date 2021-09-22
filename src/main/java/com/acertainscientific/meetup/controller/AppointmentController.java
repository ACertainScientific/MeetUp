package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.dto.AppointmentAddDto;
import com.acertainscientific.meetup.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppointmentController {

    @Autowired
    IAppointmentService iAppointmentService;

    @PostMapping("/add_appointment")
    public boolean addAppointment(@RequestBody AppointmentAddDto appointmentAddDto){
        return iAppointmentService.addAppointmentService(appointmentAddDto);
    }

    @DeleteMapping("/delete_appointment")
    public boolean deleteAppointment(@RequestParam Integer id){
        return iAppointmentService.deleteAppointmentService(id);
    }
}
