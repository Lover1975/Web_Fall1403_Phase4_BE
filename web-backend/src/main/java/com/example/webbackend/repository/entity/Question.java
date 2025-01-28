package com.example.webbackend.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "designer_id")
    private Person designer;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer1;

    @Column(nullable = false)
    private String answer2;

    @Column(nullable = false)
    private String answer3;

    @Column(nullable = false)
    private String answer4;

    @Column(nullable = false)
    private Integer correctAnswer;

    @Column(nullable = false)
    private Integer hardness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryName", referencedColumnName = "category_name", nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "answeredQuestions", fetch = FetchType.LAZY)
    private Set<Person> answeredPersons;
}
