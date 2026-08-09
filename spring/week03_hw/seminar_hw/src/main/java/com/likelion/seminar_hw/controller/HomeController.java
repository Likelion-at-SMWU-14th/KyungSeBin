package com.likelion.seminar_hw.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

   @RequestMapping("/home/{color}")
    public String home(
           @PathVariable String color, Model page){
       page.addAttribute("username", "likelion");
       page.addAttribute("color",color);
       return "home";
   }
}
