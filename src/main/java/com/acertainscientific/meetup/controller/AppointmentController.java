package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.AppointmentAddDto;
import com.acertainscientific.meetup.dto.AppointmentUpdateDto;
import com.acertainscientific.meetup.service.IAppointmentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "Appointment")
public class AppointmentController {

    @Autowired
    IAppointmentService iAppointmentService;

    @PostMapping("/appointment/add")
    public AjaxResult addAppointment(@RequestBody AppointmentAddDto appointmentAddDto, HttpServletRequest request){
        if(iAppointmentService.addAppointmentService(appointmentAddDto)){
            return AjaxResult.success().auth(request);
        }else{
            return AjaxResult.error().auth(request);
        }
    }

    @DeleteMapping("/appointment/delete")
    public AjaxResult deleteAppointment(@RequestParam Integer id, HttpServletRequest request){
        if( iAppointmentService.deleteAppointmentService(id)){
            return AjaxResult.success().auth(request);
        }else{
            return AjaxResult.error().auth(request);
        }
    }

    @PostMapping("/appointment/update")
    public AjaxResult updateAppointment(@RequestBody AppointmentUpdateDto appointmentUpdateDto, HttpServletRequest request){
        if (iAppointmentService.updateAppointmentService(appointmentUpdateDto)){
            return AjaxResult.success().auth(request);
        }else{
            return AjaxResult.error().auth(request);
        }
    }

    @GetMapping("/appointment/list")
    public AjaxResult listAppointment(@RequestParam (value = "page") Integer page,
                                      @RequestParam(value = "page-size") Integer pageSize,
                                      @RequestParam(value = "room-id",required = false, defaultValue = "") Integer roomId,
                                      @RequestParam(value = "start-time", required = false, defaultValue = "") Integer startTime,
                                      @RequestParam(value = "end-time", required = false, defaultValue = "") Integer endTime,
                                      @RequestParam(value = "user-id", required = false, defaultValue = "") String userId,
                                      @RequestParam(value = "month", required = false, defaultValue = "") Integer month,
                                      @RequestParam(value = "year", required = false, defaultValue = "") Integer year,
                                      @RequestParam(value = "date", required = false, defaultValue = "") Integer date, HttpServletRequest request){
        return AjaxResult.success(iAppointmentService.listAppointment(page, pageSize, roomId, startTime, endTime,
                userId, month, year, date)).auth(request);
    }

    @GetMapping("/appointment/detail")
    public  AjaxResult detailAppointment(@RequestParam(value = "id") Integer id, HttpServletRequest request){
        if(iAppointmentService.appointmentDecision(id)){
            return AjaxResult.success(iAppointmentService.detailAppointment(id)).auth(request);
        }
        return AjaxResult.error().auth(request);
    }


}
