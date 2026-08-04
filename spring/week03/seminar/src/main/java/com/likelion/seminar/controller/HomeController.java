package com.likelion.seminar.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/home/{color}")
    public String home(@PathVariable String color, Model page){
        page.addAttribute("username", "likelion");
        page.addAttribute("color",color);
        return "home";
    }
}
