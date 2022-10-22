package com.prudential.assignment.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prudential.assignment.repository.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {
}
