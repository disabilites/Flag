package com.springboot.flag.service;

import com.springboot.flag.domain.User;
import com.springboot.flag.domain.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(key="#name",value="FlagCache")
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }

    @Override
    @Cacheable(key="#id",value="FlagCache")
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User checkCode(String code) {
        return userMapper.checkCode(code);
    }

    @Override
    public String checkEmail(String email) {
        return userMapper.checkEmail(email);
    }

    @Override
    @CacheEvict(value="FlagCache", allEntries=true)
    public int addUser(User usr) {
        return userMapper.addUser(usr);
    }

    @Override
    @CacheEvict(value="FlagCache", allEntries=true)
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    @CacheEvict(value="FlagCache", allEntries=true)
    public int activate(String name) {
        return userMapper.activate(name);
    }

    @Override
    public int addCode(String name, String code) {
        return userMapper.addCode(name, code);
    }

    @Override
    public int clearCode(){
        return userMapper.clearCode();
    }

    @Override
    @CacheEvict(value="FlagCache", allEntries=true)
    public int deleteUser() {
        return userMapper.deleteUser();
    }
}
