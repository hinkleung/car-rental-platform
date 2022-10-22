package com.prudential.assignment.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.prudential.assignment.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * reservation table
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("reservation")
public class Reservation extends BaseEntity {

    private Long carId;

    private Long userId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime returnTime;

    /**
     * {@link com.prudential.assignment.enums.ReservationStatus}
     */
    private String status;

}
