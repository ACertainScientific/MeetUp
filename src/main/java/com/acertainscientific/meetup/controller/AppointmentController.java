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

    @PostMapping("/add-appointment")
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

//    @GetMapping("/list_appointment")
//    public AjaxResult listAppointment(@RequestParam (value = "page") Integer page,
//                                      @RequestParam(value = "page-size") Integer pageSize,
//                                      @RequestParam(value = "roomId",required = false, defaultValue = "") Integer roomId,
//                                      @RequestParam(value = "startTime", required = false, defaultValue = "") Integer startTime,
//                                      @RequestParam(value = "endTime", required = false, defaultValue = "") Integer endTime,
//                                      @RequestParam(value = "userId", required = false, defaultValue = "") String userId,
//                                      @RequestParam(value = "month", required = false, defaultValue = "") Integer month,
//                                      @RequestParam(value = "year", required = false, defaultValue = "") Integer year,
//                                      @RequestParam(value = "date", required = false, defaultValue = "") Integer date){
//        return AjaxResult.success(iAppointmentService.listAppointment(page, pageSize, roomId, startTime, endTime,
//                userId, month, year, date));
//    }

    @GetMapping("/detail-appointments")
    public  AjaxResult detailAppointment(@RequestParam(value = "id") Integer id){
        if(iAppointmentService.appointmentDecision(id)){
            return AjaxResult.success(iAppointmentService.detailAppointment(id));
        }
        return AjaxResult.error();
    }


}
