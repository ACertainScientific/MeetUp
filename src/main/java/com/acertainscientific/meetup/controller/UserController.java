package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.UserAddDto;
import com.acertainscientific.meetup.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("list-user")
    public AjaxResult listUser(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "page-size") Integer pageSize,
                               @RequestParam(value = "name") String name){
        return AjaxResult.success(userService.listUser(page, pageSize, name));
    }

    @PostMapping("/add-user")
    public AjaxResult addUser(@RequestBody UserAddDto userAddDto){
        userService.addUser(userAddDto);
        return AjaxResult.success();
    }
}
