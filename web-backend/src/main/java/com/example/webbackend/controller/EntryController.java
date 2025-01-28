package com.example.webbackend.controller;

import com.example.webbackend.jwt.JWTUtil;
import com.example.webbackend.repository.PersonRepository;
import com.example.webbackend.repository.entity.Person;
import com.example.webbackend.repository.entity.dtos.ModelMapperInstance;
import com.example.webbackend.repository.entity.dtos.PersonDto;
import com.example.webbackend.repository.entity.dtos.StringObjectMapDto;
import com.example.webbackend.web.BaseResponse;
import com.example.webbackend.web.ResponseHeader;
import com.example.webbackend.controller.services.PersonService;
import com.example.webbackend.repository.entity.enums.PersonType;
import org.springframework.web.bind.annotation.*;

@RestController
public class EntryController {

    private final PersonRepository personRepository;
    private final PersonService personService;

    public EntryController(PersonRepository personRepository,
                           PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    @PostMapping("/signin")
    public BaseResponse<StringObjectMapDto> signin(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String personType
    ) {
        try {
            if (!personService.existsPersonByUserName(username)) {
                return (new BaseResponse<>(ResponseHeader.USERNAME_NOT_EXISTS, null));
            }
            Person person = personService.findPersonByUsername(username);
            if (!person.getPersonType().equals(PersonType.valueOf(personType.toUpperCase()))) {
                return (new BaseResponse<>(ResponseHeader.WRONG_ROLE, null));
            }
            if (!person.getPassword().equals(password)) {
                return(new BaseResponse<>(ResponseHeader.WRONG_PASSWORD, null));
            }
            PersonDto personDto = ModelMapperInstance.getModelMapper().map(person, PersonDto.class);
            String jwt = JWTUtil.generateToken(username);
            StringObjectMapDto dto = new StringObjectMapDto();
            dto.put("token", jwt);
            dto.put("username", username);
            //dto.put("personType", personDto.getPersonType().toString());
            dto.put("id", person.getId());
            System.out.println("SignIn - Generated token = " + dto.getData());
            return new BaseResponse<>(ResponseHeader.OK, dto);

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(ResponseHeader.ERROR, null);
        }
    }

    @PostMapping("/signup")
    public BaseResponse<StringObjectMapDto> signup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String personType
    ) {
        if (personRepository.findByUsername(username).isPresent()) {
            return new BaseResponse<>(ResponseHeader.USERNAME_NOT_EXISTS, null);
        }
        Person person = personService.createPerson(username, password, PersonType.valueOf(personType));
        PersonDto personDto = ModelMapperInstance.getModelMapper().map(person, PersonDto.class);
        return(new BaseResponse<>(ResponseHeader.OK, personDto));
    }
}
