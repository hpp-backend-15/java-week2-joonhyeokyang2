package com.hhplus.course.lecture.presentation.controller;

import java.time.LocalDate;

public record LectureEnrollRequest(
        String lectureId,
        String userId,
        LocalDate date
) {
}
