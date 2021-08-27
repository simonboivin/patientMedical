package fr.sboivin.springdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @RequestMapping("/add")
    @ResponseBody
    public String addPatient() {
        return "<h1>Bonjour le monde</h1>";
    }
}
