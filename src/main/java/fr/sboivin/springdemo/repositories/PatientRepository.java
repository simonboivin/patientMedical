package fr.sboivin.springdemo.repositories;

import fr.sboivin.springdemo.entities.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
}