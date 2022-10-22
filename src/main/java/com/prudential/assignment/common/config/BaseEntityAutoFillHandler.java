package com.prudential.assignment.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.prudential.assignment.common.RequestContext;
import com.prudential.assignment.common.constants.BaseEntityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Objects;

/**
 * Mybatis-Plus auto fill entity field
 */
@Slf4j
public class BaseEntityAutoFillHandler implements MetaObjectHandler {

    /**
     * auto fill when insert
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        long now = System.currentTimeMillis();
        Object creatorId = metaObject.getValue(BaseEntityConstant.CREATOR_ID);
        Object updaterId = metaObject.getValue(BaseEntityConstant.UPDATER_ID);
        Object createTime = metaObject.getValue(BaseEntityConstant.CREATE_TIME);
        Object updateTime = metaObject.getValue(BaseEntityConstant.UPDATE_TIME);

        Object userId = RequestContext.getUserId();

        if (Objects.isNull(creatorId)) {
            creatorId = userId;
        }

        if (Objects.isNull(updaterId)) {
            updaterId = userId;
        }

        if (Objects.isNull(createTime)) {
            createTime = now;
        }

        if (Objects.isNull(updateTime)) {
            updateTime = now;
        }

        this.strictInsertFill(metaObject, BaseEntityConstant.CREATOR_ID, Long.class, (Long) creatorId);
        this.strictInsertFill(metaObject, BaseEntityConstant.UPDATER_ID, Long.class, (Long) updaterId);
        this.strictInsertFill(metaObject, BaseEntityConstant.CREATE_TIME, Long.class, (Long) createTime);
        this.strictInsertFill(metaObject, BaseEntityConstant.UPDATE_TIME, Long.class, (Long) updateTime);
    }

    /**
     * auto fill when update
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        long now = SystemClock.now();
        Object updaterId = metaObject.getValue(BaseEntityConstant.UPDATER_ID);
        Object updateTime = metaObject.getValue(BaseEntityConstant.UPDATE_TIME);

        Object userId = RequestContext.getUserId();

        if (Objects.isNull(updaterId)) {
            updaterId = userId;
        }

        if (Objects.isNull(updateTime)) {
            updateTime = now;
        }

        this.strictUpdateFill(metaObject, BaseEntityConstant.UPDATER_ID, Long.class, (Long) updaterId);
        this.strictUpdateFill(metaObject, BaseEntityConstant.UPDATE_TIME, Long.class, (Long) updateTime);
    }

}
