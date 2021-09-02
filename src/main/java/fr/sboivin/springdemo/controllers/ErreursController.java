package fr.sboivin.springdemo.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErreursController implements ErrorController {

    @RequestMapping("/error")
    public String erreur(Model model, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            model.addAttribute("entete_titre", "Erreur " + Integer.valueOf(status.toString()).toString());
            model.addAttribute("erreur_texte", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));

        }
        return "/common/error";
    }

}
