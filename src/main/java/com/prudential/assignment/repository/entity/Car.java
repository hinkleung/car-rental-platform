package com.prudential.assignment.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.prudential.assignment.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * car table
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("car")
public class Car extends BaseEntity {

    /**
     * 车型
     */
    private String carModel;

    /**
     * 库存
     */
    private Integer stock;

}
