package com.hhplus.course.register.domain;

import com.hhplus.course.lecture.domain.LectureItemId;
import com.hhplus.course.user.domain.UserId;

import java.util.Optional;

public interface RegisterRepository {
    Register save(Register register);
    Optional<Register> findByLectureItemIdAndStudentIdIn(LectureItemId lectureItemId, UserId userId);
}
