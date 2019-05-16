package com.springboot.flag.web;

import com.springboot.flag.domain.ResponseResult;
import com.springboot.flag.domain.User;
import com.springboot.flag.service.EmailService;
import com.springboot.flag.service.UserService;
import com.springboot.flag.utils.UUIDUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PasswordController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "forgetPassword";
    }

    @PostMapping("/checkUser")
    @ResponseBody
    public ResponseResult checkUser(String name){
        ResponseResult responseResult = new ResponseResult();
        User user = userService.findUserByName(name);
        if (user != null){
            String code = UUIDUtils.getUUID();
            userService.addCode(user.getName(), code);
            System.out.println(user.getEmail());
            emailService.sendVerifyEmail(user.getEmail(), code, "修改密码", "verifyPassword");
            responseResult.setCode(code);
            responseResult.setState(true);
        }else {
            responseResult.setState(false);
        }
        return responseResult;
    }

    @RequestMapping("/changePassword/{code}")
    public String changePassword(@PathVariable String code, Model model){
        User user = userService.checkCode(code);
        if (user != null){
            model.addAttribute("code", code);
            return "changePassword";
        }else {
            throw new NullPointerException("验证码错误");
        }
    }

    @PostMapping("/change")
    @ResponseBody
    public ResponseResult change(String code, String password){
        System.out.println(code);
        System.out.println(password);
        User user = userService.checkCode(code);
        ResponseResult responseResult = new ResponseResult();
        if (user != null){
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            String encodedPassword = new SimpleHash("SHA-512", password, salt, 1).toHex();
            user.setSalt(salt);
            user.setPassword(encodedPassword);
            user.setCode("");
            userService.updateUser(user);
            responseResult.setState(true);
            responseResult.setMsg("修改成功，请重新登陆");
        }
        else {
            responseResult.setState(false);
            responseResult.setMsg("发生未知错误！");
        }
        return responseResult;
    }
}
