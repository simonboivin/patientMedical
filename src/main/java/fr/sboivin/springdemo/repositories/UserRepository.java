package fr.sboivin.springdemo.repositories;

import fr.sboivin.springdemo.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    public User findByEmail(String username);

}
