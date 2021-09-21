package fr.sboivin.springdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String homePage() {
        return "index";
    }

    @GetMapping(value="/hello")
    public String helloWord() {
        return "hello";
    }
}

