package com.example.webbackend.controller;

import com.example.webbackend.controller.services.CategoryService;
import com.example.webbackend.controller.services.PersonService;
import com.example.webbackend.controller.services.QuestionService;
import com.example.webbackend.repository.entity.Person;
import com.example.webbackend.repository.entity.dtos.ProfileDto;
import com.example.webbackend.repository.entity.dtos.ProfilesDto;
import com.example.webbackend.repository.entity.enums.PersonType;
import com.example.webbackend.web.BaseResponse;
import com.example.webbackend.web.ResponseHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class ScoreBoardController {
    private final QuestionService questionService;
    private final PersonService personService;
    private final CategoryService categoryService;

    public ScoreBoardController(QuestionService questionService, PersonService personService, CategoryService categoryService) {
        this.questionService = questionService;
        this.personService = personService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "get-score-board")
    public BaseResponse getScoreBoard() {
        System.out.println("ScoreBoardController.get-score-board called...");
        List<Person> allPersons = personService.findAllPersons();
        System.out.println("All persons size: " + allPersons.size());
        for (Person p : allPersons) {
            Integer score = p.getScore();
            System.out.println("User: " + p.getUsername() + " score=" + score);
        }

        List<Person> top10 = allPersons.stream()
                .sorted((p1, p2) -> {
                    Integer s1 = p1.getScore() == null ? 0 : p1.getScore();
                    Integer s2 = p2.getScore() == null ? 0 : p2.getScore();
                    return Integer.compare(s2, s1);
                })
                .limit(10)
                .toList();

        System.out.println("top10 size after sort: " + top10.size());
        List<ProfileDto> top10Dto = new LinkedList<>();
        for (Person person : top10) {
            System.out.println("In top10: " + person.getUsername() + " => Score=" + person.getScore());
            if (person.getPersonType() != PersonType.DESIGNER) {
                Integer finalScore = person.getScore() == null ? 0 : person.getScore();
                ProfileDto profileDto = new ProfileDto(
                        person.getId(),
                        person.getUsername(),
                        person.getFollowersCount(),
                        person.getFollowingCount(),
                        person.getQuestions().size(),
                        person.getAnsweredQuestions().size(),
                        finalScore
                );
                top10Dto.add(profileDto);
            }
        }

        ProfilesDto profilesDto = new ProfilesDto(top10Dto);
        return new BaseResponse<>(ResponseHeader.OK, profilesDto);
    }

    @PostMapping(value = "follow-action")
    public BaseResponse followAction(@RequestParam Long followerId, @RequestParam Long targetUserId) {
        System.out.println("ScoreBoardController.followAction => followerId=" + followerId
                + ", targetUserId=" + targetUserId);

        if (personService.isFollowing(followerId, targetUserId)) {
            return new BaseResponse<>(ResponseHeader.ALREADY_FOLLOWING, null);
        }
        personService.followUser(followerId, targetUserId);
        return new BaseResponse<>(ResponseHeader.OK, null);
    }

    @PostMapping(value = "unfollow-action")
    public BaseResponse unfollowAction(@RequestParam Long followerId, @RequestParam Long targetUserId) {
        System.out.println("ScoreBoardController.unfollowAction => followerId=" + followerId
                + ", targetUserId=" + targetUserId);

        if (!personService.isFollowing(followerId, targetUserId)) {
            return new BaseResponse<>(ResponseHeader.NOT_FOLLOWING, null);
        }

        personService.unfollowPerson(followerId, targetUserId);
        return new BaseResponse<>(ResponseHeader.OK, null);
    }
}
