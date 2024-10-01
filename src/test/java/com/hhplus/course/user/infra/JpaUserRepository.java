package com.hhplus.course.user.infra;

import com.hhplus.course.user.domain.User;
import com.hhplus.course.user.domain.UserId;
import com.hhplus.course.user.domain.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, UserId> {
}
