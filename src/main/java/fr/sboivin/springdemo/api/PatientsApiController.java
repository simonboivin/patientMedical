package fr.sboivin.springdemo.api;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.services.PatientsService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/patients")
public class PatientsApiController {

    private PatientsService patientsService;

    public PatientsApiController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }


    @GetMapping(path = "", produces = "application/json")
    public List<Patient> getPatientsListAPI() {
        return patientsService.getPatientsList();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Patient getPatientByIdAPI(@PathVariable("id") int id){
        Optional<Patient> patientOptional = patientsService.getPatientById(id);
        if(patientOptional.isPresent()){
            return patientOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouvé");
        }
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public Patient editPatientByIdAPI(@PathVariable("id") int id, @RequestBody Patient patient){
        Optional<Patient> patientOptional = patientsService.getPatientById(id);
        if(patientOptional.isPresent()){
            return patientsService.editPatient(id, patient.getNom(), patient.getPrenom(), patient.getEmail(), patient.getTelephone(), patient.getVille().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouvé");
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public Patient addPatientAPI(@RequestBody Patient patient) {
        return patientsService.addPatient(patient.getNom(), patient.getPrenom(), patient.getEmail(), patient.getTelephone(), patient.getVille().getId());
    }

    @DeleteMapping(path = "/{id}")
    public void deletePatientByIdAPI(@PathVariable("id") int id){
        try {
            patientsService.deletePatientById(id);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient " + id + " n'est pas trouvé");
        }
    }

}
