package fr.sboivin.springdemo.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("bonjour")
    public String returnBonjour(Authentication authentication) {
        if (authentication != null) {
            return authentication.getName();
        } else {
        return "Non connecté";}
    }
}