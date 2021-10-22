package com.acertainscientific.meetup.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("appointment")
public class AppointmentModel {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer roomId;

    private Integer startTime;

    private Integer endTime;

    private Integer userId;

    private Integer month;

    private Integer year;

    private Integer date;

    private Integer isDeleted;

    /**
     * created_at
     */
    private Integer createdAt;

    /**
     * updated_at
     */
    private Integer updatedAt;

    /**
     * deleted_at
     */
    private Integer deletedAt;
}
