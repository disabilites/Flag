package com.springboot.flag.service;

import com.springboot.flag.domain.Flag;
import com.springboot.flag.domain.FlagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlagServiceImpl implements FlagService {

    @Autowired
    FlagMapper flagMapper;

    @Override
    @Cacheable(key="#user_id",value="FlagCache")
    public List<Flag> findFlagsByUserId(int user_id) {
        return flagMapper.findFlagsByUserId(user_id);
    }

    @Override
    public Flag findFlagById(int id) {
        return flagMapper.findFlagById(id);
    }

    @Override
    public List<Flag> findFlagsByRemind() {
        return flagMapper.findFlagsByRemind();
    }

    @Override
    @CacheEvict(value="FlagCache", allEntries=true)
    public int updateFlagById(int id, Flag flag) {
        Flag newFlag = flagMapper.findFlagById(id);
        newFlag.setTitle(flag.getTitle());
        newFlag.setContent(flag.getContent());
        newFlag.setRemind(flag.getRemind());
        newFlag.setTop(flag.getTop());
        newFlag.setRemindTime(flag.getRemindTime());
        newFlag.setSaveTime(flag.getSaveTime());
        return flagMapper.updateFlagById(newFlag);
    }

    @Override
    @CacheEvict(value="FlagCache", allEntries=true)
    public int addNewFlag(Flag flag) {
        return flagMapper.addNewFlag(flag);
    }

    @Override
    @CacheEvict(value="FlagCache", allEntries=true)
    public int deleteFlag(int id) {
        return flagMapper.deleteFlag(id);
    }
}
