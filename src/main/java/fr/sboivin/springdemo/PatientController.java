package fr.sboivin.springdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @RequestMapping("/add")
    public String addPatient(Model model) {
        model.addAttribute("message","Hello World");
        return "add_patient";
    }
}
