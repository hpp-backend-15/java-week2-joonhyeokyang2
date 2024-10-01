package com.hhplus.course.lecture.application.service;

import com.hhplus.course.lecture.domain.*;
import com.hhplus.course.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureManager {
    private final LectureRepository lectureRepository;

    @Transactional
    public void enroll(LectureId lectureId, UserId userId, LocalDate date) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow();
        lecture.join(userId, date);
    }

    @Transactional
    public void create(LectureId lectureId, LocalDate localDate) {
        List<LectureItem> lectureItems = new ArrayList<>();
        lectureItems.add(LectureItem.of(LectureItemId.of("lectureItem1"), LocalDate.now(), new ArrayList<>()));
        Lecture lecture = Lecture.from("lecture1", "heo jae", lectureItems);
        lectureRepository.save(lecture);
    }

    public Lecture findById(LectureId lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow();
    }
}
