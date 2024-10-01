package com.hhplus.course.lecture.domain;

import java.time.LocalDate;
import java.util.Optional;

public interface LectureRepository {
    Optional<Lecture> findById(LectureId lectureId);

    Optional<LectureItem> findByIdAndLectureItem_LecturingDate(LectureId lectureId, LocalDate lectureItemLecturingDate);

    Lecture save(Lecture lecture);
}
