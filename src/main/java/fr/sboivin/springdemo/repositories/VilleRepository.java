package fr.sboivin.springdemo.repositories;

import fr.sboivin.springdemo.entities.Ville;
import org.springframework.data.repository.CrudRepository;

public interface VilleRepository extends CrudRepository<Ville, Integer> {
}
