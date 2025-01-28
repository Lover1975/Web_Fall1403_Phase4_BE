package com.example.webbackend.controller.services;

import com.example.webbackend.repository.entity.Person;
import com.example.webbackend.repository.entity.Question;
import com.example.webbackend.repository.entity.enums.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.webbackend.repository.PersonRepository;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository userRepository) {
        this.personRepository = userRepository;
    }

    @CacheEvict(value = { "allPersons" }, allEntries = true)
    @Transactional
    public Person createPerson(String username, String password, PersonType personType) {
        Person person = new Person();
        person.setUsername(username);
        person.setPassword(password);
        person.setPersonType(personType);
        person.setScore(0);

        return personRepository.save(person);
    }

    @Transactional
    public void recordUserAnsweredQuestion(String username, Question question) {
        Person person = findPersonByUsername(username);
        person.getAnsweredQuestions().add(question);
        personRepository.save(person);
    }

    @CacheEvict(value = {
            "allQuestions",
            "questionsByCategory",
            "questionsByPerson",
            "profileByUsername",
            "feedForUser",
            "personByUsername",
            "allPersons",
            "allCategories",
            "categoryByName"
    }, allEntries = true)
    @Transactional
    public void followUser(Long followerId, Long targetUserId) {
        Person follower = personRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        Person targetUser = personRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        if (follower.equals(targetUser)) {
            throw new RuntimeException("Users cannot follow themselves");
        }

        follower.followPerson(targetUser);
        personRepository.save(follower);
    }

    @CacheEvict(value = {
            "allQuestions",
            "questionsByCategory",
            "questionsByPerson",
            "profileByUsername",
            "feedForUser",
            "personByUsername",
            "allPersons",
            "allCategories",
            "categoryByName"
    }, allEntries = true)
    @Transactional
    public void unfollowPerson(Long followerId, Long targetUserId) {
        Person follower = personRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        Person targetUser = personRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        follower.unfollowPerson(targetUser);
        personRepository.save(follower);
    }

    public boolean existsPersonByUserName(String username) {
        return personRepository.existsByUsername(username);
    }

    public List<Person> findDesignersByUsername(String partialName) {
        return personRepository.findDesignersByUsernameLike(PersonType.DESIGNER, partialName);
    }

    @Cacheable(value = "personByUsername", key = "#username")
    public Person findPersonByUsername(String username) {
        return personRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Person not found"));
    }

    @Cacheable(value = "allPersons")
    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public boolean isFollowing(Long followerId, Long targetUserId) {
        Person follower = personRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        Person targetUser = personRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        return follower.getFollowing().contains(targetUser);
    }


    @CacheEvict(value = {
            "allQuestions",
            "questionsByCategory",
            "questionsByPerson",
            "profileByUsername",
            "feedForUser",
            "personByUsername",
            "allPersons",
            "allCategories",
            "categoryByName"
    }, allEntries = true)
    @Transactional
    public void addScore(String username, int score) {
        Person person = findPersonByUsername(username);
        person.setScore(person.getScore() + score);
        personRepository.save(person);
    }
}