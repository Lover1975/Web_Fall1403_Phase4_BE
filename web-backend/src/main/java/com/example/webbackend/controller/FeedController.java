package com.example.webbackend.controller;

import com.example.webbackend.controller.services.FeedService;
import com.example.webbackend.repository.entity.Person;
import com.example.webbackend.repository.entity.dtos.QuestionDto;
import com.example.webbackend.repository.entity.dtos.QuestionsDto;
import com.example.webbackend.web.BaseResponse;
import com.example.webbackend.web.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.webbackend.repository.PersonRepository;
import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;
    private final PersonRepository personRepository;

    @Autowired
    public FeedController(FeedService feedService, PersonRepository personRepository) {
        this.feedService = feedService;
        this.personRepository = personRepository;
    }

    @GetMapping
    public BaseResponse<QuestionsDto> getFeed(@RequestParam String username) {
        Person user = personRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<QuestionDto> feedQuestions = feedService.getFeedForUser(user.getId());
        QuestionsDto questionsDto = new QuestionsDto(feedQuestions);
        return new BaseResponse<>(ResponseHeader.OK, questionsDto);
    }
}
