package fr.sboivin.springdemo.security;

import fr.sboivin.springdemo.entities.User;
import fr.sboivin.springdemo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = userRepository.findByEmail(username);
        System.out.println("Username = "+username);
        System.out.println("User = "+ user);
        if(user==null){
            throw new UsernameNotFoundException("Pas d'utilisateur nommé " + username);
        } else {
            return new UserDetailsImpl(user);
        }
    }
}
