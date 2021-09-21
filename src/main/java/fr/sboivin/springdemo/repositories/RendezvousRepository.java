package fr.sboivin.springdemo.repositories;

import fr.sboivin.springdemo.entities.Rendezvous;
import org.springframework.data.repository.CrudRepository;

public interface RendezvousRepository extends CrudRepository<Rendezvous, Integer> {
}
