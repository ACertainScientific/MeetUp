package com.acertainscientific.meetup.mapper;

import com.acertainscientific.meetup.model.RoomModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
public interface RoomMapper extends BaseMapper<RoomModel> {

}
