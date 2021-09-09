package fr.sboivin.springdemo.api;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.services.PatientsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/{id}", produces = "application/json")
    public Patient getPatientById(@PathVariable("id") int id){
        Optional<Patient> patientOptional = patientsService.getPatientById(id);
        if(patientOptional.isPresent()){
            return patientOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouvé");
        }
    }


    @PostMapping(path = "", produces = "application/json")
    public Patient addPatient(@RequestBody Patient patient) {
        return patientsService.addPatient(patient.getNom(), patient.getPrenom(), patient.getEmail(), patient.getTelephone(), patient.getVille().getId());
    }



}
