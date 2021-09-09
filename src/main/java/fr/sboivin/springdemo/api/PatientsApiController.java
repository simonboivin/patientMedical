package fr.sboivin.springdemo.api;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.services.PatientsService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "", produces = "application/json")
    public Patient addPatient(@RequestBody Patient patient) {
        return patientsService.addPatient(patient.getNom(), patient.getPrenom(), patient.getEmail(), patient.getTelephone(), patient.getVille().getId());
    }

}
