package fr.sboivin.springdemo.controllers;

import fr.sboivin.springdemo.services.RendezvousService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {

    private final RendezvousService rendezvousService;

    public HomeController(RendezvousService rendezvousService) {
        this.rendezvousService = rendezvousService;
    }

    @GetMapping
    public String homePage(Model model) {
        System.out.println(rendezvousService.compteRendezVousMensuels());
        model.addAttribute("compte", rendezvousService.compteRendezVousMensuels());
        return "index";
    }

    @GetMapping(value="/hello")
    public String helloWord() {
        return "hello";
    }
}

