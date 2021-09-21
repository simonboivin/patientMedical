package fr.sboivin.springdemo.repositories;

import fr.sboivin.springdemo.entities.Rendezvous;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezvousRepository extends CrudRepository<Rendezvous, Integer> {

    List<Rendezvous> findAllByDateheureBetween(
            LocalDateTime publicationTimeStart,
            LocalDateTime publicationTimeEnd);

    Rendezvous findFirstByOrderByDateheureAsc();

    Rendezvous findFirstByOrderByDateheureDesc();

}
