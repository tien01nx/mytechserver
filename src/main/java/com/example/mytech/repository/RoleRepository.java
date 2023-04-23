package com.example.mytech.repository;

import com.example.mytech.entity.ERole;
import com.example.mytech.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role , String> {

    Optional<Role> findByName (ERole name) ;
}
