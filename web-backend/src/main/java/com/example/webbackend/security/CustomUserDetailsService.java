package com.example.webbackend.security;

import com.example.webbackend.repository.PersonRepository;
import com.example.webbackend.repository.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // حالا می‌توانیم از کلاس User با نقش‌ها یا از User.UserBuilder استفاده کنیم
        // برای سادگی فقط از نقش USER استفاده می‌کنم
        return User.withUsername(person.getUsername())
                .password(person.getPassword()) // پسورد هش‌شده
                .roles("USER") // یا بسته به فیلد personType
                .build();
    }
}
