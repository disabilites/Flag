package com.springboot.flag.domain;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Select("select * from user where name = #{name} and state = 1")
    User findUserByName(String name);

    @Select("select * from user where id = #{id} and state = 1")
    User findUserById(int id);

    @Select("select * from user where code = #{code}")
    User checkCode(String code);

    @Select("select email from user where email = #{email}")
    String checkEmail(String email);

    @Insert("insert into user(name, email, password, salt, state, code, registertime) " +
            "values(#{name}, #{email}, #{password}, #{salt}, #{state}, #{code}, #{registerTime})")
    int addUser(User user);

    @Update("update user set salt = #{salt}, password = #{password}, code = #{code} where name = #{name}")
    int updateUser(User user);

    @Update("update user set state = 1, code = '' where name = #{name}")
    int activate(String name);

    @Update("update user set code = #{code} where name = #{name}")
    int addCode(@Param("name") String name, @Param("code") String code);

    @Update("update user set code = ''")
    int clearCode();

    @Delete("delete from user where state = 0")
    int deleteUser();
}
