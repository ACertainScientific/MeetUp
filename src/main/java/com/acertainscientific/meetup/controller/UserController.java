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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Api(tags = "User")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/list-user")
    @ApiOperation(value = "list-user", notes = "return the list of all users")
    public AjaxResult listUser(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "page-size") Integer pageSize,
                               @RequestParam(value = "name" ,required = false, defaultValue = "") String name, HttpServletRequest request){
        return AjaxResult.success(userService.listUser(page, pageSize, name)).auth(request);
    }

    @PostMapping("/sign-up")
    @ApiOperation(httpMethod = "POST",value = "sign up", notes = "return: 0=>success 1=>userName already exist 2=>email already exist")
    public AjaxResult addUser(@RequestBody UserAddDto userAddDto, HttpServletRequest request){
        return AjaxResult.success(userService.addUser(userAddDto));
    }

    @DeleteMapping("/delete-user")
    @ApiOperation(httpMethod = "DELETE",value = "delete user", notes = "Delete the certain user with given ID, return error if the user does not exist")
    public AjaxResult deleteUser(@RequestParam Integer id, HttpServletRequest request){
        if(userService.deleteUser(id)){
            return AjaxResult.success().auth(request);
        }
        return AjaxResult.error().auth(request);
    }

    @PostMapping("/login")
    @ApiOperation(value = "login", notes = "returnL 0=>success 1=>no userName found 2=> no=>email found")
    public AjaxResult login(@RequestBody UserAddDto userAddDto, HttpServletResponse httpServletResponse, HttpServletRequest request){
        LoginInfo loginInfo = userService.login(userAddDto);
        if(loginInfo.getCode() ==0){
            httpServletResponse.setHeader("X-Authorization", JavaWebToken.createJavaWebToken(loginInfo.getToken()));
        }
        //httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.addHeader("Access-Control-Allow-Headers", "*");
//        httpServletResponse.addHeader("Access-Control-Allow-Methods", "*");
        loginInfo.setAuth(JavaWebToken.createJavaWebToken(loginInfo.getToken()));
        loginInfo.setToken(null);
        return AjaxResult.success(loginInfo);
    }

}
