package com.hhplus.course.lecture.domain;

import java.util.Optional;

public interface LectureRepository {
    Optional<Lecture> findById(LectureId lectureId);
    Lecture save(Lecture lecture);
}
