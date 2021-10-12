package com.acertainscientific.meetup.service;

import com.acertainscientific.meetup.dto.PageResponseDto;
import com.acertainscientific.meetup.model.UserModel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<UserModel> {
    PageResponseDto listUser(Integer page, Integer pageSize, String name);
}