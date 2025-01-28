package com.example.webbackend.controller.services;

import com.example.webbackend.repository.QuestionRepository;
import com.example.webbackend.repository.entity.Person;
import com.example.webbackend.repository.entity.dtos.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.example.webbackend.repository.PersonRepository;
@Service
public class ProfileService {

    private final PersonRepository personRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public ProfileService(PersonRepository personRepository, QuestionRepository questionRepository) {
        this.personRepository = personRepository;
        this.questionRepository = questionRepository;
    }

    public boolean existsUser(String username) {
        return personRepository.existsByUsername(username);
    }

    @Cacheable(value = "profileByUsername", key = "#username")
    public ProfileDto getProfile(String username) {

        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        int followerCount = person.getFollowers().size();
        long id = person.getId();
        int followingCount = person.getFollowing().size();
        int questionCount = questionRepository.findByDesigner(person).size();
        int answeredCount = person.getAnsweredQuestions().size();
        int score = person.getScore();
        ProfileDto profileDto = new ProfileDto(id, username, followerCount, followingCount, questionCount, answeredCount, score);
        return profileDto;
    }
}
