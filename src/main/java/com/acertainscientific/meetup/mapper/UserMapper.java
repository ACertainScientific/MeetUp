package com.acertainscientific.meetup.mapper;

import com.acertainscientific.meetup.model.UserModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper extends BaseMapper<UserModel> {
    List<UserModel> listUser(@Param("name") String name);
    UserModel findByUserName(@Param("userName")String userName);
    UserModel findByEmail(@Param("email")String email);
}
