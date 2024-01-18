package com.microservice.userservice.controller;

import com.microservice.userservice.dto.AccountResponse;
import com.microservice.userservice.dto.UserRequest;
import com.microservice.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insert(@RequestBody UserRequest userRequest){
        userService.insert(userRequest);
        return "Insert successfully";
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse findAll(){
        return userService.findAll();
    }

    /*@GetMapping("/findOneById/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findOneById(@PathVariable Long id){
        return userService.findOneById(id);
    }*/

}
