package com.acertainscientific.meetup.service;

import com.acertainscientific.meetup.dto.PageResponseDto;
import com.acertainscientific.meetup.dto.UserAddDto;
import com.acertainscientific.meetup.model.UserModel;
import com.acertainscientific.meetup.pojo.LoginInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<UserModel> {
    PageResponseDto listUser(Integer page, Integer pageSize, String name);

    Integer addUser(UserAddDto userAddDto);

    boolean deleteUser(Integer id);

    /**
     *
     * @param userAddDto
     * @return 0-> login success
     *          1-> cannot find userName/email
     *          2-> wrong password
     */
    LoginInfo login(UserAddDto userAddDto);

}
