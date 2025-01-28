package com.example.webbackend.repository.entity;

import com.example.webbackend.repository.entity.enums.PersonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Person {
    public Person() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column Integer score;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_followers",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<Person> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<Person> following = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "designer")
    private Set<Question> questions = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_answered_questiones",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> answeredQuestions = new HashSet<Question>();

    // Helper methods for managing followers/following relationships
    public void followPerson(Person personToFollow) {
        personToFollow.getFollowers().add(this);
        this.following.add(personToFollow);
    }

    public void unfollowPerson(Person personToUnfollow) {
        personToUnfollow.getFollowers().remove(this);
        this.following.remove(personToUnfollow);
    }

    public boolean isFollowing(Person person) {
        return this.following.contains(person);
    }

    public int getFollowersCount() {
        return this.followers.size();
    }

    public int getFollowingCount() {
        return this.following.size();
    }

}
