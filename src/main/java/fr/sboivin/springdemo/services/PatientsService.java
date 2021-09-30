package fr.sboivin.springdemo.services;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.Ville;
import fr.sboivin.springdemo.repositories.PatientRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Iterable<Patient> getPatientsList(String search) {
        if (search == null | search.length() == 0) {
            return patientRepository.findAll();
        } else {
            return patientRepository.findByNomContainsOrPrenomContains(search, search);
        }
    }

    public List<Patient> getPatientsList() {
        return (List<Patient>) patientRepository.findAll();
    }

    /**
     * Retourne un patient identifié par son ID
     *
     * @param id ID du patient
     * @return Patient
     */
    @Transactional
    public Optional<Patient> getPatientById(int id) {
        return patientRepository.findById(id);
    }

    public boolean existPatientByEmail(String email) {
        return patientRepository.existsByEmail(email);
    }

    /**
     * Set les différents attributs pour un objet patient
     */
    private void configurePatient(Patient patient, String nom, String prenom, String email, String telephone, int villeID) {
        patient.setNom(nom);
        patient.setPrenom(prenom);
        patient.setEmail(email);
        patient.setTelephone(telephone);
        Optional<Ville> villeOptional = villesService.getVillebyId(villeID);
        villeOptional.ifPresent(patient::setVille);
    }

    /**
     * Ajoute un patient à la base
     */
    @Transactional
    public Patient addPatient(String nom, String prenom, String email, String telephone, int villeID) {
        Patient patient = new Patient();
        configurePatient(patient, nom, prenom, email, telephone, villeID);
        patientRepository.save(patient);
        return patient;
    }

    /**
     * *Edite un patient dans la base
     *
     * @return patient édité
     */
    @Transactional
    public Patient editPatient(int id, String nom, String prenom, String email, String telephone, int villeID) {
        Optional<Patient> patientOptional = getPatientById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            configurePatient(patient, nom, prenom, email, telephone, villeID);
            patientRepository.save(patient);
            return patient;
        } else {
            throw new ObjectNotFoundException(id, "Patient non trouvé");
        }
    }

    /**
     * Supprime un patient identifié par son id
     */
    @Transactional
    public void deletePatientById(int id) {
        Optional<Patient> patientOptional = getPatientById(id);
        if (patientOptional.isPresent()) {
            patientRepository.delete(patientOptional.get());
        } else {
            throw new ObjectNotFoundException(id, "Patient non trouvé");
        }
    }


}
