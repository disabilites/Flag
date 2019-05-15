package com.springboot.flag.web;

import com.springboot.flag.domain.Flag;
import com.springboot.flag.service.FlagService;
import com.springboot.flag.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class FlagController {

    @Autowired
    FlagService flagService;

    @Autowired
    UserService userService;

    @RequestMapping({"/","/flag"})
    public String showFlag(Model model){
        String name  = (String) SecurityUtils.getSubject().getPrincipal();
        int user_id = userService.findUserByName(name).getId();
        List<Flag> flags = flagService.findFlagsByUserId(user_id);
        model.addAttribute("flags", flags);
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping("/flag/set")
    public String setFlag(){
        return "set";
    }

    @RequestMapping("/flag/edit/{id}")
    public String editFlag(@PathVariable int id, Model model){
        Flag flag = flagService.findFlagById(id);
        model.addAttribute("flag", flag);
        return "edit";
    }

    @RequestMapping("/flag/delete/{id}")
    public String deleteFlag(@PathVariable int id){
        flagService.deleteFlag(id);
        return "redirect:/";
    }

    @RequestMapping("/flag/save/{id}")
    @ResponseBody
    public void saveFlag(@PathVariable int id, Flag flag){
        flagService.updateFlagById(id, flag);
    }

    @RequestMapping("/flag/save")
    @ResponseBody
    public void saveFlag(Flag flag){
        flag.setUser_id(userService.findUserByName((String) SecurityUtils.getSubject().getPrincipal()).getId());
        flagService.addNewFlag(flag);
    }
}