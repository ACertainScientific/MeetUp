package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.AppointmentAddDto;
import com.acertainscientific.meetup.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppointmentController {

    @Autowired
    IAppointmentService iAppointmentService;

    @PostMapping("/add_appointment")
    public AjaxResult addAppointment(@RequestBody AppointmentAddDto appointmentAddDto){
        if(iAppointmentService.addAppointmentService(appointmentAddDto)){
            return AjaxResult.success();
        }else{
            return AjaxResult.error();
        }
    }

    @DeleteMapping("/delete_appointment")
    public AjaxResult deleteAppointment(@RequestParam Integer id){
        if( iAppointmentService.deleteAppointmentService(id)){
            return AjaxResult.success();
        }else{
            return AjaxResult.error();
        }
    }
}
