package com.springboot.flag.service;

import com.springboot.flag.domain.Flag;

import java.util.Date;
import java.util.List;

public interface FlagService {

    List<Flag> findFlagsByUserId(int user_id);

    Flag findFlagById(int id);

    List<Flag> findFlagsByRemind();

    int updateFlagById(int id, Flag flag);

    int addNewFlag(Flag flag);

    int deleteFlag(int id);
}
