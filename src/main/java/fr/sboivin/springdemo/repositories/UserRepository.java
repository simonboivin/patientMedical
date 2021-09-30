package fr.sboivin.springdemo.repositories;

import fr.sboivin.springdemo.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    public Optional<User> findByEmail(String email);


}
