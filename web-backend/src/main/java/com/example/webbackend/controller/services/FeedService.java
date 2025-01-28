package com.example.webbackend.controller.services;

import com.example.webbackend.repository.QuestionRepository;
import com.example.webbackend.repository.entity.Person;
import com.example.webbackend.repository.entity.Question;
import com.example.webbackend.repository.entity.enums.PersonType;
import com.example.webbackend.repository.entity.dtos.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.webbackend.repository.PersonRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final PersonRepository personRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public FeedService(PersonRepository personRepository,
                       QuestionRepository questionRepository) {
        this.personRepository = personRepository;
        this.questionRepository = questionRepository;
    }

    @Cacheable(value = "feedForUser", key = "#userId")
    public List<QuestionDto> getFeedForUser(Long userId) {
        Person user = personRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Person> allFollowing = user.getFollowing();
        List<Person> followingDesigners = allFollowing.stream()
                .filter(person -> person.getPersonType() == PersonType.DESIGNER)
                .collect(Collectors.toList());

        if (followingDesigners.isEmpty()) {
            return List.of();
        }

        List<Question> questions = questionRepository.findAllByDesignerIn(followingDesigners);

        return questions.stream().map(q -> new QuestionDto(
                q.getId(),
                q.getDesigner().getUsername(),
                q.getQuestion(),
                q.getAnswer1(),
                q.getAnswer2(),
                q.getAnswer3(),
                q.getAnswer4(),
                q.getCategory().getCategoryName(),
                q.getCorrectAnswer().toString(),
                q.getHardness().toString()
        )).collect(Collectors.toList());
    }
}
