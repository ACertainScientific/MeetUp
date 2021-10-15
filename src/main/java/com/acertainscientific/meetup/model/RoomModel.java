package com.acertainscientific.meetup.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@TableName("room")
public class RoomModel {
    /**
     * id
     */
    @TableId(type= IdType.AUTO)
    private Integer id;
    /**
     * e.g. DCC308
     */
    private String roomName;

    /**
     *
     * e.g. 1
     */
    private Integer floor;

    /**
     *
     * e.g. 1
     */
    private Integer buildingId;

    /**
     * createdAt
     */
    private Integer createdAt;

    /**
     * updatedAt
     */
    private Integer updatedAt;

    /**
     * deletedAt
     */
    private Integer deletedAt;


    private Integer isDeleted;

    /**
     * status
     * 0=avaliable 1=occupied 2=dirty
     */
    private Integer status;
}
