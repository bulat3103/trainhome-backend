package com.example.trainhome.configuration;

import com.example.trainhome.entities.Person;
import com.example.trainhome.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final PersonService personService;

    @Autowired
    public CustomUserDetailsService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.findByEmail(username);
        return CustomUserDetails.convertUserToUserDetails(person);
    }
}
