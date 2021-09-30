package fr.sboivin.springdemo.security;

import fr.sboivin.springdemo.entities.User;
import fr.sboivin.springdemo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return new UserDetailsImpl(user.get());
        } else {
            System.out.println("Username = " + username);
            throw new UsernameNotFoundException("Pas d'utilisateur nommé " + username);
        }
    }
}
