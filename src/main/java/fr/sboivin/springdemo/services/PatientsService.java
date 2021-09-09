package fr.sboivin.springdemo.services;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.repositories.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Optional;

@Service
public class PatientsService {

    private final PatientRepository patientRepository;
    private final VillesService villesService;

    public PatientsService(PatientRepository patientRepository, VillesService villesService) {
        this.patientRepository = patientRepository;
        this.villesService = villesService;
    }

    /**
     * @return Liste des Patients de la base
     */
    @Transactional
    public List<Patient> getPatientsList() {
        return (List<Patient>) patientRepository.findAll();
    }

    /**
     * Retourne un patient identifié par son ID
     * @param id ID du patient
     * @return Patient
     */
    @Transactional
    public Optional<Patient> getPatientById(int id){
       return patientRepository.findById(id);
    }


    /**
     * Ajoute un patient à la base
     * @param nom
     * @param prenom
     * @param email
     * @param telephone
     * @param villeID
     */
    @Transactional
    public Patient addPatient(String nom, String prenom, String email, String telephone, int villeID) {
        Patient patient = new Patient();
        patient.setNom(nom);
        patient.setPrenom(prenom);
        patient.setEmail(email);
        patient.setTelephone(telephone);
        Optional<Ville> villeOptional = villesService.getVillebyId(villeID);
        if (villeOptional.isPresent()) {
            patient.setVille(villeOptional.get());
        }
        patientRepository.save(patient);
        return patient;
    }

}
