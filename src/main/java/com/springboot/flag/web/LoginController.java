package com.springboot.flag.web;

import com.springboot.flag.domain.ResponseResult;
import com.springboot.flag.domain.User;
import com.springboot.flag.service.UserService;
import com.springboot.flag.utils.getIpAddr;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/signIn")
    public String signIn(){
        return "signIn";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult login(User user) {
        ResponseResult responseResult = new ResponseResult();
        if (userService.findUserByName(user.getName()) == null ||
                userService.findUserByName(user.getName()).getState() == 0){
            responseResult.setState(false);
            responseResult.setMsg("用户名或密码错误");
        }else {
            Subject subject = SecurityUtils.getSubject();
            String name = user.getName();
            String password = user.getPassword();
            UsernamePasswordToken token = new UsernamePasswordToken(name, password, true);

            try {
                subject.login(token);
                if (subject.isAuthenticated() == true){
                    responseResult.setState(true);
                }
            }catch (AuthenticationException e){
                e.printStackTrace();
                responseResult.setMsg("用户名或密码错误");
            }
        }
        return responseResult;
    }
}
