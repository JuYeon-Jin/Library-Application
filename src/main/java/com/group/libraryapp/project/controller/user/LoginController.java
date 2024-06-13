package com.group.libraryapp.project.controller.user;

import com.group.libraryapp.project.dto.user.UserDTO;
import com.group.libraryapp.project.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homepage(Model model) {
        return "view/user/home";
    }

    // 회원 가입
    @PostMapping("/join")
    public String join(UserDTO dto) {
        userService.joinProcess(dto);
        return "redirect:/";
    }

    // 로그인 → 페이지 분기 ADMIN / USER
    @GetMapping("/authBranch")
    public ModelAndView login(ModelAndView mav) {

        if (userService.checkRole().equals("ROLE_ADMIN")) {
            mav.setViewName("/view/admin/myBook");
        } else {
            mav.setViewName("/view/user/myBook");
            // mav.addObject("id", id);
        }

        return mav;
    }


}
