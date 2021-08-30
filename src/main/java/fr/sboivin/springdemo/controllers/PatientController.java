package fr.sboivin.springdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/patients")
public class PatientController {

    @RequestMapping(value = "/")
    public String hello(Model model) {
        model.addAttribute("message", "Hello World");
        return "hello";
    }

    @RequestMapping(value = "/add")
    public String addPatient(Model model) {
        model.addAttribute("entete_titre", "Ajouter patient");
        model.addAttribute("placeholder_nom", "Nom*");
        model.addAttribute("placeholder_prenom", "Prénom*");
        model.addAttribute("placeholder_mail", "Email*");
        model.addAttribute("placeholder_telephone", "Téléphone*");
        model.addAttribute("button_submit_text","Ajouter patient");
        return "patients/add_edit";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editPatient(Model model, @PathVariable long id) {
        return "patients/add_edit";
    }

    @RequestMapping(value = "/list")
    public String listAll(Model model) {
        return "patients/list";
    }

    @RequestMapping(value = "/{id}")
    public String viewPatient(Model model, @PathVariable int id) {

        model.addAttribute("entete_titre", "Patient ID " + Integer.toString(id));
        model.addAttribute("value_nom", "Dalton");
        model.addAttribute("value_prenom", "Avrel*");
        model.addAttribute("value_mail", "avrel@gg.io");
        model.addAttribute("value_telephone", "0145247000");
        model.addAttribute("button_submit_text","Mettre à jour");
        return "patients/add_edit";
    }

}
