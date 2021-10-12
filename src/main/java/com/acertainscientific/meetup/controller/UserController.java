package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
