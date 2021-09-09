package fr.sboivin.springdemo.services;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientsService {

    private final PatientRepository patientRepository;

    public PatientsService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     *
     * @return Liste des Patients de la base
     */
    public List<Patient> getPatientsList(){
        return (List<Patient>) patientRepository.findAll();
            }


}
