package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.BuildingUpdateDto;
import com.acertainscientific.meetup.dto.DetailRoomDto;
import com.acertainscientific.meetup.dto.RoomAddDto;
import com.acertainscientific.meetup.dto.RoomUpdateDto;
import com.acertainscientific.meetup.model.RoomModel;
import com.acertainscientific.meetup.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {
    @Autowired
    IRoomService roomService;

    @GetMapping("/room-search/{id}")
    public AjaxResult searchById(@PathVariable Integer id) {
        RoomModel roomModel = roomService.searchById(id);
        if (roomModel == null || roomModel.getIsDeleted() == 1){
            return AjaxResult.error();
        }

        return AjaxResult.success(roomModel);
    }

    @PostMapping("/add-room")
    public AjaxResult addRoom(@RequestBody RoomAddDto roomAddDto){
        if (roomService.addRoom(roomAddDto)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @PostMapping("/delete-room")
    public AjaxResult deleteRoom(@RequestParam Integer id){
        if (roomService.deleteRoom(id)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @GetMapping("/detail-room")
    public AjaxResult detailBuilding(@RequestParam(value = "id") Integer id){
        if(roomService.roomDecision(id)){
            return AjaxResult.success(roomService.detailRoomDto(id));
        }
        return AjaxResult.error();
    }

    @GetMapping("/list-room")
    public AjaxResult ListRoom(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "page-size") Integer pageSize,
                               @RequestParam(value = "building-id") Integer buildingId,
                               @RequestParam(value = "floor") Integer floor,
                               @RequestParam(value = "room-name",required = false) String roomName){
        return AjaxResult.success(roomService.listRoom(page, pageSize, buildingId, floor, roomName));
    }

    @PostMapping("/update-room")
    public AjaxResult updateBuilding(@RequestBody RoomUpdateDto roomUpdateDto){
        if(roomService.roomUpdate(roomUpdateDto)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
