package fr.sboivin.springdemo.services;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.Rendezvous;
import fr.sboivin.springdemo.repositories.RendezvousRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RendezvousService {

    private RendezvousRepository rendezvousRepository;
    private PatientsService patientsService;

    public RendezvousService(RendezvousRepository rendezvousRepository, PatientsService patientsService) {
        this.rendezvousRepository = rendezvousRepository;
        this.patientsService = patientsService;
    }

    /**
     * Créér un nouveau rendez-vous
     *
     * @param patientID ID du patient associé au rendez-vous
     * @param dateheure Date et Heure du début du rendez-vous
     * @param type      Type du rendez-vous
     * @param duree     Durée du rendez-vous en heure
     * @param note      Notes
     * @return Rendez-vous créé
     */
    @Transactional
    public Rendezvous addRendezvous(int patientID, LocalDateTime dateheure, String type, int duree, String note) {
        try {
            Rendezvous rendezvous = configureRendezvous(new Rendezvous(), patientID, dateheure, type, duree, note);
            return rendezvousRepository.save(rendezvous);
        } catch (ObjectNotFoundException e) {
            throw e;
        }
    }

    /**
     * Obtenir la liste des rendez-vous

     */
    public List<Rendezvous> getAllRendezvous() {
        return (List<Rendezvous>) rendezvousRepository.findAll();
    }

    /**
     * Obtenir un rendez-vous
     * @param id ID du rendez-vous
     */
    public Optional<Rendezvous> getRendezvousById(Integer id) {
        return rendezvousRepository.findById(id);
    }

    /**
     * Editer un rendez-vous
     */
    public Rendezvous updateRendezvousById(Integer rendezvousId, int patientId, LocalDateTime dateheure, String type, int duree, String note) {
        Optional<Rendezvous> rendezvousOptional = getRendezvousById(rendezvousId);
        if (rendezvousOptional.isPresent()) {
            return rendezvousRepository.save(configureRendezvous(rendezvousOptional.get(), patientId, dateheure, type, duree, note));
        } else {
            throw new ObjectNotFoundException(rendezvousId, "Rendez-vous " + rendezvousId + " non trouvé");
        }
    }

    /**
     * Supprimer un rendez-vous
     */
    public void deleteRendezvousById(Integer rendezvousId){
        Optional<Rendezvous> rendezvousOptional = getRendezvousById(rendezvousId);
        if (rendezvousOptional.isPresent()) {
            rendezvousRepository.delete(rendezvousOptional.get());
        } else {
            throw new ObjectNotFoundException(rendezvousId, "Rendez-vous " + rendezvousId + " non trouvé");
        }
    }

    private Rendezvous configureRendezvous(Rendezvous rendezvous, int patientId, LocalDateTime dateheure, String type, int duree, String note) {
        Optional<Patient> patientOptional = patientsService.getPatientById(patientId);
        if (patientOptional.isPresent()) {
            rendezvous.setPatient(patientOptional.get());
            rendezvous.setDateheure(dateheure);
            rendezvous.setType(type);
            rendezvous.setDuree(duree);
            rendezvous.setNote(note);
            return rendezvous;
        } else {
            throw new ObjectNotFoundException(patientId, "Patient " + patientId + " non trouvé");
        }
    }

}
