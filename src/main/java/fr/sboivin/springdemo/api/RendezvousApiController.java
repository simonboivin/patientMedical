package fr.sboivin.springdemo.api;

import fr.sboivin.springdemo.entities.Patient;
import fr.sboivin.springdemo.entities.Rendezvous;
import fr.sboivin.springdemo.services.RendezvousService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("api/rdv")
public class RendezvousApiController {

    private final RendezvousService rendezvousService;

    RendezvousApiController( RendezvousService rendezvousService) {
        this.rendezvousService = rendezvousService;
    }

    @GetMapping(path="", produces = "application/json")
    public Iterable<Rendezvous> getRendezvousListApi() {
        return rendezvousService.getAllRendezvous();
    }


    @GetMapping(path = "/{id}", produces = "application/json")
    public Rendezvous getRendezVousByIdAPI(@PathVariable("id") int id) {
        Optional<Rendezvous> rdvOptional = rendezvousService.getRendezvousById(id);
        if (rdvOptional.isPresent()) {
            return rdvOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le rendez-vous " + id + " n'a pas été trouvé");
        }
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public Rendezvous editRendezvousByIdAPI(@PathVariable("id") int id, @RequestBody Rendezvous rendezvous) {
        Optional<Rendezvous> rdvOptionnal = rendezvousService.getRendezvousById(id);
        if (rdvOptionnal.isPresent()) {
            return rendezvousService.updateRendezvousById(id, rendezvous.getPatient().getId(),rendezvous.getDateheure(), rendezvous.getType(),rendezvous.getDuree(), rendezvous.getNote());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le rendez-vous " + id + " n'a pas été trouvé");
        }
    }

    @PostMapping(path = "", produces = "application/json")
    public Rendezvous addRendezvousAPI(@RequestBody Rendezvous rendezvous) {
        return rendezvousService.addRendezvous(rendezvous.getPatient().getId(), rendezvous.getDateheure(), rendezvous.getType(),rendezvous.getDuree(),rendezvous.getNote());
    }

    @DeleteMapping(path = "/{id}")
    public void deleteRendezvousByIdAPI(@PathVariable("id") int id) {
        try {
            rendezvousService.deleteRendezvousById(id);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le rendez-vous " + id + " n'a pas été trouvé");
        }
    }

}
