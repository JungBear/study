package com.LoginPractice.User;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 회원가입 폼
     * @return
     */
    @GetMapping("/signUp")
    public String signUpForm() {
        return "signup";
    }

    /**
     * 회원가입 진행
     * @param user
     * @return
     */
    @PostMapping("/signUp")
    public String signUp(UserVo userVo) {
        userService.joinUser(userVo);
        return "redirect:/login"; //로그인 구현 예정
    }
}