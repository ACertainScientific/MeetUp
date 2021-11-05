package com.acertainscientific.meetup.controller;

import com.acertainscientific.meetup.common.AjaxResult;
import com.acertainscientific.meetup.dto.UserAddDto;
import com.acertainscientific.meetup.pojo.LoginInfo;
import com.acertainscientific.meetup.service.IUserService;
import com.acertainscientific.meetup.util.JavaWebToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Api(tags = "User")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/list-user")
    public AjaxResult listUser(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "page-size") Integer pageSize,
                               @RequestParam(value = "name" ,required = false, defaultValue = "") String name){
        return AjaxResult.success(userService.listUser(page, pageSize, name));
    }

    @PostMapping("/sign-up")
    @ApiOperation(httpMethod = "POST",value = "sign up", notes = "return: 0=>success 1=>userName already exist 2=>email already exist")
    public AjaxResult addUser(@RequestBody UserAddDto userAddDto){
        return AjaxResult.success(userService.addUser(userAddDto));
    }

    @DeleteMapping("/delete-user")
    public AjaxResult deleteUser(@RequestParam Integer id){
        if(userService.deleteUser(id)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @PostMapping("/login")
    @ApiOperation(value = "login", notes = "returnL 0=>success 1=>no userName found 2=> no=>email found")
    public AjaxResult login(@RequestBody UserAddDto userAddDto, HttpServletResponse httpServletResponse){
        LoginInfo loginInfo = userService.login(userAddDto);
        if(loginInfo.getCode() ==0){
            httpServletResponse.setHeader("X-Authorization", JavaWebToken.createJavaWebToken(loginInfo.getToken()));
        }
        return AjaxResult.success(loginInfo.getCode());
    }

}
