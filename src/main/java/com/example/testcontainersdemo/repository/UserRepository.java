package com.example.testcontainersdemo.repository;

import com.example.testcontainersdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByemail(String email);
}
