package com.springboot.flag.domain;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FlagMapper {

    @Select("select * from flag where user_id = #{user_id} order by top desc, savetime desc")
    List<Flag> findFlagsByUserId(int user_id);

    @Select("select * from flag where id = #{id}")
    Flag findFlagById(int id);

    @Select("select user_id, content, remindtime from flag where remind = 1")
    List<Flag> findFlagsByRemind();

    @Update("update flag set title = #{title}, content = #{content}, remind = #{remind}, top = #{top}, " +
            "remindtime = #{remindTime}, savetime = #{saveTime} where id = #{id}")
    int updateFlagById(Flag flag);

    @Insert("insert into flag(user_id, title, content, remind, top, remindtime, savetime) values(#{user_id}, #{title}," +
            "#{content}, #{remind}, #{top}, #{remindTime}, #{saveTime})")
    int addNewFlag(Flag flag);

    @Delete("delete from flag where id = #{id}")
    int deleteFlag(int id);
}
