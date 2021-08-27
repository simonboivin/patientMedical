package fr.sboivin.springdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @RequestMapping("/add")
    public String addPatient() {
        return "add_patient";
    }
}
