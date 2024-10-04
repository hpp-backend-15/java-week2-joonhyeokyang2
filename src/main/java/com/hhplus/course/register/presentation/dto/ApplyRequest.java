package com.hhplus.course.register.presentation.dto;

import java.time.LocalDate;

public record ApplyRequest(
        String lectureId,
        String userId,
        LocalDate date
) {
}

