package com.acertainscientific.meetup.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@TableName("user")
public class UserModel {
    /**
     * id
     */
    @TableId(type= IdType.AUTO)
    private Integer id;
    /**
     * User
     */
    private String userName;
    /**
     * password
     */
    private String password;

    /**
     * email
     */
    private String email;
    /**
     * isDelete
     */
    private Integer isDeleted;
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

    /**
     * type
     */
    private Integer type;


}
