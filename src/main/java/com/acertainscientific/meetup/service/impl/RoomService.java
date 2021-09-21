package com.acertainscientific.meetup.service.impl;

import com.acertainscientific.meetup.mapper.RoomMapper;
import com.acertainscientific.meetup.model.RoomModel;
import com.acertainscientific.meetup.service.IRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RoomService extends ServiceImpl<RoomMapper, RoomModel> implements IRoomService {
    @Autowired
    RoomMapper roomMapper;



    @Override
    public RoomModel searchById(int id){
        return roomMapper.selectById(id);
    }
}
