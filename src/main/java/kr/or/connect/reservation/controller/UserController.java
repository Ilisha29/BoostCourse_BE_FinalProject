package kr.or.connect.reservation.controller;

import kr.or.connect.reservation.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/users")
public class UserController {
    // 스프링 컨테이너가 생성자를 통해 자동으로 주입한다.
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/loginform")
    public String loginform(){
        return "users/loginform";
    }

    @RequestMapping("/loginerror")
    public String loginerror(@RequestParam("login_error")String loginError){
        return "users/loginerror";
    }

}