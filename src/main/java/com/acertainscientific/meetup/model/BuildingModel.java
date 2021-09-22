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

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * e.g Library, Gym, Sage
     */
    private String name;

    /**
     *
     * e.g 1
     */
    private Integer floor_start;

    /**
     *
     * e.g 3, 4
     */
    private Integer floor_end;

    /**
     * created_at
     */
    private Integer created_at;

    /**
     * updated_at
     */
    private Integer updated_at;

    /**
     * deleted_at
     */
    private Integer deleted_at;

    /**
     * is_deleted
     */
    private Integer is_deleted;
}
