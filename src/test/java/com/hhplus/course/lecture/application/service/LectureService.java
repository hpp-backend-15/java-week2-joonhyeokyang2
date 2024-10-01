package com.hhplus.course.lecture.application.service;

import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.lecture.domain.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {
    private final LectureRepository lectureRepository;

    public Lecture findById(LectureId lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow();
    }

    public LectureItem findLectureItemByLectureIdAndDate(LectureId lectureId, LocalDate date) {
        return lectureRepository.findByIdAndLectureItem_LecturingDate(lectureId, date).orElseThrow();
    }
}
