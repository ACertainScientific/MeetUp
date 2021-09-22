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

    private Integer room_id;

    private Integer start_time;

    private Integer end_time;

    private String user_id;

    private Integer month;

    private Integer year;

    private Integer date;
}
