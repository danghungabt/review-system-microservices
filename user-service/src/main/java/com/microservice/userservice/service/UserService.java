package com.microservice.userservice.service;

import com.microservice.userservice.constant.SystemConstant;
import com.microservice.userservice.dto.AccountResponse;
import com.microservice.userservice.dto.RoleResponse;
import com.microservice.userservice.dto.UserRequest;
import com.microservice.userservice.dto.UserResponse;
import com.microservice.userservice.model.Role;
import com.microservice.userservice.model.User;
import com.microservice.userservice.repository.RoleRepository;
import com.microservice.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void insert(UserRequest userRequest){
        User user = new User();
        Role role = roleRepository.findFirstByCode(userRequest.getRoleCode()).get();
        user.setUserName(userRequest.getUserName());
        user.setFullName(userRequest.getFullName());
        user.setPassword(SystemConstant.PASSWORD_DEFAULT);
        user.getRoles().add(role);

        userRepository.save(user);
    }

    public UserResponse findOneById(Long id){
        return mapToUserResponse(userRepository.findById(id).get());
    }

    public AccountResponse findAll(){
        AccountResponse result = new AccountResponse();
        result.setUserResponseList(userRepository.findAll().stream().map(this::mapToUserResponse).toList());
        result.setRoleResponses(roleRepository.findAll().stream().map(this::mapToRoleResponse).toList());
        return result;
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .build();
    }

    private RoleResponse mapToRoleResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .code(role.getCode())
                .name(role.getName())
                .build();
    }
}
