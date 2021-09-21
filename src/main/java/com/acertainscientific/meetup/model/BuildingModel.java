package com.acertainscientific.meetup.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@TableName("building")
public class BuildingModel {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer floor_start;

    private Integer floor_end;

    private Integer created_at;

    private Integer updated_at;

    private Integer deleted_at;
}
