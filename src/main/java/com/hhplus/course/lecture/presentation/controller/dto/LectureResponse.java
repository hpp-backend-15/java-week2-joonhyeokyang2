package com.hhplus.course.lecture.presentation.controller.dto;

import com.hhplus.course.lecture.domain.Lecture;

public record LectureResponse(
        String lectureId,
        String title,
        String lecturer
) {
    public static LectureResponse of(Lecture lecture) {
        return new LectureResponse(
                lecture.getId().getId(),
                lecture.getTitle(),
                lecture.getLecturer().getName()
        );
    }
}
