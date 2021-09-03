package fr.sboivin.springdemo.controllers;


import fr.sboivin.springdemo.entities.User;
import fr.sboivin.springdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private final UserRepository ur;

    public GlobalControllerAdvice(UserRepository ur) {
        this.ur = ur;
    }

    @ModelAttribute("bonjour")
    public String returnBonjour(Authentication authentication) {
        if (authentication != null) {
            User u = ur.findByEmail(authentication.getName());
            return u.getName();
        } else {
            return "Non connecté";
        }
    }


    @ModelAttribute("avatar")
    public String avatarURL(Authentication authentication) {
        if (authentication != null) {
            User u = ur.findByEmail(authentication.getName());
            System.out.println(u.getPhotouser());
            return "/profils/"+ u.getPhotouser();
        } else {
            return "/img/user-defaut.png";
        }

    }
}