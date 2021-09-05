package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.User;
import fr.sboivin.springdemo.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UtilisateursController {

    final UserRepository ur;

    public UtilisateursController(UserRepository ur) {
        this.ur = ur;
    }

    @RequestMapping(value = "/list")
    public String listAll(Model model) {
        List<User> lu = (List<User>) ur.findAll();
        model.addAttribute("liste_utilisateurs", lu);
        return "utilisateurs/list";
    }
}
