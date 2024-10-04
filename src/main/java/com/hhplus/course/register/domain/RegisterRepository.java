package com.hhplus.course.register.domain;

import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureItemId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface RegisterRepository {

    Register save(Register register);

    List<Register> findByLectureIdIn(List<LectureId> lectureIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Register> findByLectureItemId(LectureItemId lectureItemId);
}
