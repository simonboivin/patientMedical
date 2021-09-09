package fr.sboivin.springdemo.api;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.services.PatientsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ws/patients")
public class PatientsApiController {

    private PatientsService patientsService;

    public PatientsApiController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }


    @GetMapping(path = "", produces = "application/json")
    public List<Patient> getPatientsList() {
        return patientsService.getPatientsList();
    }

}
