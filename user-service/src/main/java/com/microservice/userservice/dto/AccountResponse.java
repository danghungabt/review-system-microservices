package com.microservice.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse
{
    List<UserResponse> userResponseList;
    List<RoleResponse> roleResponses;
}
