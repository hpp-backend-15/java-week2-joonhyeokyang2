package com.hhplus.course.lecture.presentation.controller;

public record LectureEnrollRequest(
        String lectureId,
        String userId
) {
}
