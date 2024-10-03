package com.hhplus.course.lecture.infra.repository;

import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLectureRepository extends LectureRepository, JpaRepository<Lecture, LectureId> {

}
