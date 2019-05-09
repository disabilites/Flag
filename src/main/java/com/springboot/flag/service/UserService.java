package com.springboot.flag.service;

import com.springboot.flag.domain.User;

public interface UserService {

    User findUserByName(String name);

    User findUserById(int id);

    User checkCode(String code);

    String checkEmail(String email);

    int addUser(User usr);

    int updateUser(User user);

    int activate(String name);

    int addCode(String name, String code);

    int clearCode();

    int deleteUser();
}
