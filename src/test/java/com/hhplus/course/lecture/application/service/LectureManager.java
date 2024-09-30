package com.hhplus.course.lecture.application.service;

import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureRepository;
import com.hhplus.course.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureManager {
    private final LectureRepository lectureRepository;

    @Transactional
    public void enroll(LectureId lectureId, UserId userId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow();
        lecture.join(userId);
    }

    @Transactional
    public void create(LectureId lectureId, LocalDate localDate) {
        Lecture lecture = Lecture.from("lecture1", localDate);
    }


    public Lecture findById(LectureId lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow();
    }
}
