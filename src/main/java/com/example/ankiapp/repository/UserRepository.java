package com.example.ankiapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ankiapp.entitiy.User2;


public interface UserRepository extends JpaRepository<User2, Long> {

}
