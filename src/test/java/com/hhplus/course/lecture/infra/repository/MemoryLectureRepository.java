package com.hhplus.course.lecture.infra.repository;

import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureRepository;

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
    public Lecture save(Lecture lecture) {
        return lecture;
    }
}
