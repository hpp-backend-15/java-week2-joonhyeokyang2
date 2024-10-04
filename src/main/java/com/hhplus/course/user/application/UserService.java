package com.hhplus.course.user.application;

import com.hhplus.course.user.domain.User;
import com.hhplus.course.user.domain.UserId;
import com.hhplus.course.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(UserId id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
