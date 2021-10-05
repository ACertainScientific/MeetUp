package com.acertainscientific.meetup.service;

import com.acertainscientific.meetup.dto.DetailRoomDto;
import com.acertainscientific.meetup.dto.RoomAddDto;
import com.acertainscientific.meetup.dto.RoomDto;
import com.acertainscientific.meetup.model.RoomModel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IRoomService extends IService<RoomModel> {
    /**
     * search room by  id
     * @param id
     * @return
     */
    RoomModel searchById(int id);

    boolean deleteRoom(Integer id);

    boolean addRoom(RoomAddDto roomAddDto);

    boolean roomDecision(Integer id);

    DetailRoomDto detailRoomDto(Integer id);

}
