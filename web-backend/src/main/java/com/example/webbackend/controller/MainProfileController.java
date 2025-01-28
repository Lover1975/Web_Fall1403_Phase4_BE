package com.example.webbackend.controller;

import com.example.webbackend.controller.services.ProfileService;
import com.example.webbackend.repository.entity.dtos.ProfileDto;
import com.example.webbackend.web.BaseResponse;
import com.example.webbackend.web.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-profile")
public class MainProfileController {

    private final ProfileService profileService;

    @Autowired
    public MainProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public BaseResponse<ProfileDto> getProfile(@RequestParam String username) {
        ProfileDto profile = profileService.getProfile(username);
        return new BaseResponse<>(ResponseHeader.OK, profile);
    }

    @GetMapping("/search")
    public BaseResponse<ProfileDto> searchUserProfile(@RequestParam String username) {
        boolean userExists = profileService.existsUser(username);
        if (!userExists) {
            return new BaseResponse<>(ResponseHeader.USERNAME_NOT_EXISTS, null);
        }
        ProfileDto profile = profileService.getProfile(username);
        return new BaseResponse<>(ResponseHeader.OK, profile);
    }
}
