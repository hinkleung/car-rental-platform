package com.prudential.assignment.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.prudential.assignment.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * user table
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 盐
     */
    private String salt;

}
