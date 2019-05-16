package com.springboot.flag.web;

import com.springboot.flag.domain.ResponseResult;
import com.springboot.flag.domain.User;
import com.springboot.flag.service.EmailService;
import com.springboot.flag.service.UserService;
import com.springboot.flag.utils.UUIDUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @RequestMapping("/signUp")
    public String signUp(){
        return "signUp";
    }

    @RequestMapping("/wait")
    public String waitPage(){return "wait";}

    @PostMapping("/register")
    @ResponseBody
    public ResponseResult register(String name, String password, String email, HttpServletRequest request){
        ResponseResult responseResult = new ResponseResult();
        if (userService.findUserByName(name) != null){
            responseResult.setState(true);
            responseResult.setMsg("用户名已被使用");
            return responseResult;
        }else {
            if (userService.checkEmail(email) != null){
                responseResult.setState(true);
                responseResult.setMsg("邮箱已被使用");
                return responseResult;
            }
            responseResult.setState(false);
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            String encodedPassword = new SimpleHash("SHA-512", password, salt, 1).toHex();
            String code = UUIDUtils.getUUID();
            User registerUser = new User(name, encodedPassword, email, salt, code, new Date());
            userService.addUser(registerUser);
            emailService.sendVerifyEmail(email, code, "账号验证","verifyEmail");
        }
        return responseResult;
    }

    @RequestMapping("/checkCode")
    public String checkCode(Model model, String code){
        User user = userService.checkCode(code);
        if (user == null){
            model.addAttribute("msg", "发生错误，验证失败！");
            model.addAttribute("state", false);
        }else {
            userService.activate(user.getName());
            model.addAttribute("msg", "你好，" +  user.getName());
            model.addAttribute("state", true);
        }
        return "result";
    }
}
