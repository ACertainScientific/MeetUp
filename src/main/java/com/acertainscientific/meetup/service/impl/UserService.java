package com.acertainscientific.meetup.service.impl;


import com.acertainscientific.meetup.dto.PageResponseDto;
import com.acertainscientific.meetup.dto.RoomListDto;
import com.acertainscientific.meetup.dto.UserAddDto;
import com.acertainscientific.meetup.dto.UserListDto;
import com.acertainscientific.meetup.mapper.UserMapper;
import com.acertainscientific.meetup.model.RoomModel;
import com.acertainscientific.meetup.model.UserModel;
import com.acertainscientific.meetup.pojo.LoginInfo;
import com.acertainscientific.meetup.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<UserListDto> userListDtos = modelMapper.map(userModels, new TypeToken<List<UserListDto>>() {
        }.getType());

        PageResponseDto pageResponseDto = new PageResponseDto();
        pageResponseDto.setList(userListDtos);
        pageResponseDto.setTotalCount((int) pageInfo.getTotal());
        pageResponseDto.setPageCount(pageInfo.getPages());
        pageResponseDto.setPerPage(pageInfo.getPageSize());
        pageResponseDto.setCurrentPage(pageInfo.getPageNum());
        return pageResponseDto;
    }

    @Override
    public Integer addUser(UserAddDto userAddDto){
        if(userMapper.findByUserName(userAddDto.getUserName())!=null){
            return 1;
        }
        if(userMapper.findByEmail(userAddDto.getEmail())!=null){
            return 2;
        }
        UserModel userModel = modelMapper.map(userAddDto, UserModel.class);
        userModel.setPassword(DigestUtils.md5DigestAsHex(userModel.getPassword().getBytes(StandardCharsets.UTF_8)));
        userModel.setCreatedAt((int)(System.currentTimeMillis()/1000));
        this.save(userModel);
        return 0;
    }

    @Override
    public boolean deleteUser(Integer id){
        UserModel userModel = this.getById(id);
        if(userModel != null){
            userModel.setIsDeleted(1);
            userModel.setDeletedAt((int)(System.currentTimeMillis()/1000));
            this.updateById(userModel);
            return true;
        }
        return false;
    }

    @Override
    public LoginInfo login(UserAddDto userAddDto){
        UserModel userModel = null;
        LoginInfo loginInfo = new LoginInfo();
        if(userAddDto.getUserName()!=null && !userAddDto.getUserName().equals("")){
            userModel = userMapper.findByUserName(userAddDto.getUserName());
        }else if(userAddDto.getEmail()!=null && !userAddDto.getEmail().equals("")){
            userModel = userMapper.findByEmail(userAddDto.getEmail());
        }

        if(userModel == null){
            loginInfo.setCode(1);
            return loginInfo;
        }

        if(DigestUtils.md5DigestAsHex(userAddDto.getPassword().getBytes(StandardCharsets.UTF_8)).equals(userModel.getPassword())
            && userAddDto.getType().equals(userModel.getType())){
            loginInfo.setCode(0);
            Map<String,String> token= new HashMap<>();
            token.put("userName",userModel.getUserName());
            token.put("type",userModel.getType().toString());
            loginInfo.setToken(token);
            return loginInfo;
        }else{
            loginInfo.setCode(2);
            return loginInfo;
        }
    }

}
