package com.hhplus.course.lecture.presentation.controller.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// AvailableLectureResponse record
public record AvailableLectureResponse(
        String title,
        String lecturer,
        Map<LocalDate, Integer> lecturingDatesAndOpenSeats
) {

    public static class AvailableLectureResponseBuilder {
        private final String title;
        private final String lecturer;
        private final Map<LocalDate, Integer> lecturingDatesAndOpenSeats = new HashMap<>();

        public AvailableLectureResponseBuilder(String title, String lecturer) {
            this.title = title;
            this.lecturer = lecturer;
        }

        public void addLecturingDate(LocalDate date, Integer openSeats) {
            this.lecturingDatesAndOpenSeats.put(date, openSeats);
        }

        public AvailableLectureResponse build() {
            return new AvailableLectureResponse(title, lecturer, lecturingDatesAndOpenSeats);
        }
    }

}
