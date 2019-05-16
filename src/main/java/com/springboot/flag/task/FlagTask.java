package com.springboot.flag.task;

import com.springboot.flag.domain.Flag;
import com.springboot.flag.service.EmailService;
import com.springboot.flag.service.FlagService;
import com.springboot.flag.service.UserService;
import com.springboot.flag.utils.isSameDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FlagTask {

    @Autowired
    private FlagService flagService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private List<Flag> remindFlags = new ArrayList<>();

    public void getRemindFlag(){
        List<Flag> flags = flagService.findFlagsByRemind();
        for (Flag flag: flags){
            if (isSameDate.isSameDay(flag.getRemindTime(), new Date())){
                remindFlags.add(flag);
            }
        }
    }

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void getRemindList(){
        getRemindFlag();
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void test(){
        for (Flag flag: remindFlags){
            if (isSameDate.isSameMinute(flag.getRemindTime(), new Date())){
                emailService.sendRemindEmail(userService.findUserById(flag.getUser_id()).getEmail(), flag.getContent());
            }
        }
    }
}
