package com.example.webbackend.repository.entity.dtos;

import com.example.webbackend.repository.entity.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = Resolver.class,
        scope = PersonDto.class
)
@Getter
@Setter
@AllArgsConstructor
public class PersonDto extends Dto{
    @JsonProperty("id")
    private int id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("personType")
    private PersonType personType;

    @Override
    public String toString() {
        return ("Username: " + username + "\n");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PersonDto))
            return false;

        PersonDto other = (PersonDto) o;

        return (id == other.getId());
    }

    public PersonDto(){

    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
