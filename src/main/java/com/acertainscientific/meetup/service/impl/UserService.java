package com.acertainscientific.meetup.service.impl;


import com.acertainscientific.meetup.dto.PageResponseDto;
import com.acertainscientific.meetup.dto.RoomListDto;
import com.acertainscientific.meetup.dto.UserListDto;
import com.acertainscientific.meetup.mapper.UserMapper;
import com.acertainscientific.meetup.model.RoomModel;
import com.acertainscientific.meetup.model.UserModel;
import com.acertainscientific.meetup.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, UserModel> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PageResponseDto listUser(Integer page, Integer pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        List<UserModel> userModels = userMapper.listUser(name);
        PageInfo pageInfo = new PageInfo(userModels);
        List<UserListDto> userListDtos = modelMapper.map(userModels,new TypeToken<List<UserModel>>(){}.getType());

        PageResponseDto pageResponseDto= new PageResponseDto();
        pageResponseDto.setList(userListDtos);
        pageResponseDto.setTotalCount((int) pageInfo.getTotal());
        pageResponseDto.setPageCount(pageInfo.getPages());
        pageResponseDto.setPerPage(pageInfo.getPageSize());
        pageResponseDto.setCurrentPage(pageInfo.getPageNum());
        return pageResponseDto;
    }
}
