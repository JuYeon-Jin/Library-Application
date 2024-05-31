package com.group.libraryapp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/testmain")
    public String test() {
        return "redirect:/view/user/main.html";
    }
}
