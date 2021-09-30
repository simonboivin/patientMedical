package fr.sboivin.springdemo.api;

import fr.sboivin.springdemo.entities.User;
import fr.sboivin.springdemo.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/login")
public class LoginApiController {

    private final UserRepository userRepository;

    public LoginApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path="", produces = "application/json")
    public ResponseEntity<User> checkLogin(@RequestBody User userInput) {
        Optional<User> userOpt = userRepository.findByEmail(userInput.getEmail());
        if (userOpt.isPresent()){
            User user = userOpt.get();
            user.setPassword("***");
            return ResponseEntity.ok().body(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user unknown");
        }

    }

}
