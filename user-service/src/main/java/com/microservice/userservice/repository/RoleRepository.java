package com.microservice.userservice.repository;

import com.microservice.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findFirstByCode(String code);

    List<Role> findRolesByCode(String code);
}
