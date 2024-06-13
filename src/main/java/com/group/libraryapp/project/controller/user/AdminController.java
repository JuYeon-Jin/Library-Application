package com.group.libraryapp.project.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // 책 등록 PAGE
    @GetMapping("/bookForm")
    public String bookForm() {
        return "view/admin/bookForm";
    }

}
