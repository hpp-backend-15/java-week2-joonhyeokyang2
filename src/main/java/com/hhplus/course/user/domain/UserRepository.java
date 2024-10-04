package com.hhplus.course.user.domain;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId id);

    User save(User user);
}
