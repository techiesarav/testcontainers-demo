package com.example.testcontainersdemo.service;

import com.example.testcontainersdemo.dto.UserDto;
import com.example.testcontainersdemo.entity.User;
import com.example.testcontainersdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class UserService implements Comparator {
    private final UserRepository userRepository;

    public void createUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(user,userDto);
        userRepository.save(user);
    }


    public User getUserByEmail(String email) {
        return userRepository.findByemail(email);
    }

}
