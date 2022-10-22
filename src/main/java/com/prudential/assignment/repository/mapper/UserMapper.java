package com.prudential.assignment.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prudential.assignment.repository.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
