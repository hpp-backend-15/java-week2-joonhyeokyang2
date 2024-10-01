package com.hhplus.course.lecture.infra.repository;

import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.lecture.domain.LectureRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryLectureRepository implements LectureRepository {
    private final Map<LectureId, Lecture> map = new ConcurrentHashMap<>();

    @Override
    public Optional<Lecture> findById(LectureId lectureId) {
        return Optional.ofNullable(map.get(lectureId));
    }

    @Override
    public Optional<LectureItem> findByIdAndLectureItem_LecturingDate(LectureId lectureId, LocalDate lectureItemLecturingDate) {
        return Optional.of(map.get(lectureId)
                .getLectureItem().stream()
                .filter(x -> x.getLecturingDate().equals(lectureItemLecturingDate))
                .findFirst()
                .orElseThrow());
    }


    @Override
    public Lecture save(Lecture lecture) {
        return lecture;
    }
}
