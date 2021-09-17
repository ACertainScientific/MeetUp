package com.acertainscientific.meetup.service;

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
}
