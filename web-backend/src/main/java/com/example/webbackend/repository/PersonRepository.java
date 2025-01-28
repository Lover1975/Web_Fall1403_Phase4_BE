package com.example.webbackend.repository;

import com.example.webbackend.repository.entity.Person;
import com.example.webbackend.repository.entity.enums.PersonType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
    boolean existsByUsername(String username);
    @Query("SELECT p FROM Person p WHERE p.personType = :type AND LOWER(p.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    List<Person> findDesignersByUsernameLike(@Param("type") PersonType type, @Param("username") String username);


}
