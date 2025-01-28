package com.example.webbackend.repository.entity.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto extends Dto{
    @JsonProperty
    private long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("follower_count")
    private int followerCount;

    @JsonProperty("question_count")
    private int questionCount;

    @JsonProperty("following_count")
    private int followingCount;

    @JsonProperty("answered_count")
    private int answeredCount;

    @JsonProperty("score")
    private int score;
}
