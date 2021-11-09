package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.BuildingUpdateDto;
import com.acertainscientific.meetup.dto.DetailRoomDto;
import com.acertainscientific.meetup.dto.RoomAddDto;
import com.acertainscientific.meetup.dto.RoomUpdateDto;
import com.acertainscientific.meetup.model.RoomModel;
import com.acertainscientific.meetup.service.IRoomService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "Room")
public class RoomController {
    @Autowired
    IRoomService roomService;

    @GetMapping("/room/search/{id}")
    public AjaxResult searchById(@PathVariable Integer id, HttpServletRequest request) {
        RoomModel roomModel = roomService.searchById(id);
        if (roomModel == null || roomModel.getIsDeleted() == 1){
            return AjaxResult.error().auth(request);
        }

        return AjaxResult.success(roomModel).auth(request);
    }

    @PostMapping("/room/add")
    public AjaxResult addRoom(@RequestBody RoomAddDto roomAddDto, HttpServletRequest request){
        if (roomService.addRoom(roomAddDto)){
            return AjaxResult.success().auth(request);
        }
        return AjaxResult.error().auth(request);
    }

    @PostMapping("/room/delete")
    public AjaxResult deleteRoom(@RequestParam Integer id, HttpServletRequest request){
        if (roomService.deleteRoom(id)){
            return AjaxResult.success().auth(request);
        }
        return AjaxResult.error().auth(request);
    }

    @GetMapping("/room/detail")
    public AjaxResult detailBuilding(@RequestParam(value = "id") Integer id, HttpServletRequest request){
        if(roomService.roomDecision(id)){
            return AjaxResult.success(roomService.detailRoomDto(id)).auth(request);
        }
        return AjaxResult.error().auth(request);
    }

    @GetMapping("/room/list")
    public AjaxResult ListRoom(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "page-size") Integer pageSize,
                               @RequestParam(value = "building-id") Integer buildingId,
                               @RequestParam(value = "floor") Integer floor,
                               @RequestParam(value = "room-name",required = false, defaultValue = "") String roomName, HttpServletRequest request){
        return AjaxResult.success(roomService.listRoom(page, pageSize, buildingId, floor, roomName)).auth(request);
    }

    @PostMapping("/room/update")
    public AjaxResult updateBuilding(@RequestBody RoomUpdateDto roomUpdateDto, HttpServletRequest request){
        if(roomService.roomUpdate(roomUpdateDto)){
            return AjaxResult.success().auth(request);
        }
        return AjaxResult.error().auth(request);
    }

    @PostMapping("/room/checkin/{id}")
    public AjaxResult checkIn(@PathVariable Integer id, HttpServletRequest request){
        if(roomService.checkIn(id)){
            return AjaxResult.success().auth(request);
        }else{
            return AjaxResult.error().auth(request);
        }
    }

    @PostMapping("/room/clean/{id}")
    public AjaxResult clean(@PathVariable Integer id, HttpServletRequest request){
        if(roomService.clean(id)){
            return AjaxResult.success().auth(request);
        }else{
            return AjaxResult.error().auth(request);
        }
    }
}
