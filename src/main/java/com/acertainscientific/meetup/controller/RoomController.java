package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.model.RoomModel;
import com.acertainscientific.meetup.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    @Autowired
    IRoomService roomService;

    @GetMapping("/search/{id}")
    public RoomModel searchById(@PathVariable Integer id){
        return roomService.searchById(id);
    }
}
