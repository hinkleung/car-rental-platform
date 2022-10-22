package com.prudential.assignment.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prudential.assignment.common.model.request.ReservationPageRequest;
import com.prudential.assignment.enums.ReservationStatus;
import com.prudential.assignment.repository.entity.Reservation;
import com.prudential.assignment.repository.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationDao {

    @Autowired
    private ReservationMapper reservationMapper;

    public List<Reservation> selectIntersectionByTime(LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<Reservation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.ne(Reservation::getStatus, ReservationStatus.FINISHED)
                .and(qw -> qw.between(Reservation::getStartTime, startTime, endTime)
                        .or()
                        .between(Reservation::getEndTime, startTime, endTime));
        return reservationMapper.selectList(queryWrapper);
    }

    public int selectCountByTimeAndCarId(LocalDateTime startTime, LocalDateTime endTime, Long carId) {
        LambdaQueryWrapper<Reservation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Reservation::getCarId, carId)
                .ne(Reservation::getStatus, ReservationStatus.FINISHED)
                .and(qw -> qw.between(Reservation::getStartTime, startTime, endTime)
                        .or()
                        .between(Reservation::getEndTime, startTime, endTime));
        return reservationMapper.selectCount(queryWrapper);
    }

    public int insert(Reservation reservation) {
        return reservationMapper.insert(reservation);
    }

    public IPage<Reservation> selectPage(ReservationPageRequest request, Long userId) {
        int currentPage = request.getCurrentPage() == null ? 1 : request.getCurrentPage();
        int pageSize = request.getPageSize() == null ? 10 : request.getPageSize();
        IPage<Reservation> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Reservation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(request.getCarId() != null, Reservation::getCarId, request.getCarId())
                .eq(userId != null, Reservation::getUserId, userId)
                .and(request.getStartTime() != null && request.getEndTime() != null, qw -> {
                    qw.between(Reservation::getStartTime, request.getStartTime(), request.getEndTime())
                            .or()
                            .between(Reservation::getEndTime, request.getStartTime(), request.getEndTime());
                });
        return reservationMapper.selectPage(page, queryWrapper);
    }

    public Reservation selectById(Long id) {
        return reservationMapper.selectById(id);
    }

    public int update(Reservation updateEntity) {
        return reservationMapper.updateById(updateEntity);
    }
}
