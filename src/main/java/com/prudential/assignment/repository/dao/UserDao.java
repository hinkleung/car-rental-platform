package com.prudential.assignment.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.prudential.assignment.repository.entity.User;
import com.prudential.assignment.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public User selectByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, username);
        return userMapper.selectOne(queryWrapper);
    }

    public int insert(User user) {
        return userMapper.insert(user);
    }

    public List<User> selectAll() {
        return userMapper.selectList(Wrappers.lambdaQuery());
    }

    public List<User> selectByIds(List<Long> userIds) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(User::getId, userIds);
        return userMapper.selectList(queryWrapper);
    }
}
