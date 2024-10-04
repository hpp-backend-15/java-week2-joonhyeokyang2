package com.hhplus.course.register.presentation.dto;

/*
    userId - 유저 상세 페이지 이동용
    lectureId - 강좌 페이지 이동용
 */
public record ApplyResponse(
        String userId,
        String lectureId
) {
}