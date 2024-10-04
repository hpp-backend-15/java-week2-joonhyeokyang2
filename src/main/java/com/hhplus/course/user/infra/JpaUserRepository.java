package com.hhplus.course.user.infra;

import com.hhplus.course.lecture.infra.repository.LectureWithAvailableSeats;
import com.hhplus.course.user.domain.User;
import com.hhplus.course.user.domain.UserId;
import com.hhplus.course.user.domain.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, UserId> {

}
