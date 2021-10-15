package com.acertainscientific.meetup.mapper;

import com.acertainscientific.meetup.model.RoomModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoomMapper extends BaseMapper<RoomModel> {

    List<RoomModel> listRoom(@Param("buildingId")Integer buildingId, @Param("floor")Integer floor, @Param("roomName") String roomName);

    Integer searchAllByRoomId(@Param("id") Integer id);
}
