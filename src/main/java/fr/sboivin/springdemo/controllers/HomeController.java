package fr.sboivin.springdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping("/")
    public String homePage(Model model) {
        model.addAttribute("message","Bonjour");
        return "hello";
    }
}
