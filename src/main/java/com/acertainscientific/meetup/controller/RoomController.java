package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.RoomAddDto;
import com.acertainscientific.meetup.model.RoomModel;
import com.acertainscientific.meetup.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {
    @Autowired
    IRoomService roomService;

    @GetMapping("/room_search/{id}")
    public AjaxResult searchById(@PathVariable Integer id) {
        RoomModel roomModel = roomService.searchById(id);
        if (roomModel == null || roomModel.getIsDeleted() == 1){
            return AjaxResult.error();
        }

        return AjaxResult.success(roomModel);
    }

    @PostMapping("/add_room")
    public AjaxResult addRoom(@RequestBody RoomAddDto roomAddDto){
        if (roomService.addRoom(roomAddDto)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @PostMapping("/delete_room")
    public AjaxResult deleteRoom(@RequestParam Integer id){
        if (roomService.deleteRoom(id)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
