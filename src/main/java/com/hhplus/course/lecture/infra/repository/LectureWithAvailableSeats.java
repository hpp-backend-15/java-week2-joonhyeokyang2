package com.hhplus.course.lecture.infra.repository;

import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.register.domain.Register;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LectureWithAvailableSeats {

    private String lectureId;
    private String lectureItemId;
    private String title;
    private String lecturer;
    private LocalDate lecturingDate;
    private Integer openSeats;

    public LectureWithAvailableSeats(Lecture lecture, LectureItem lectureItem, Register register) {
        this.lectureId = lecture.getId().getId();
        this.lectureItemId = lectureItem.getId().getId();
        this.title = lecture.getTitle();
        this.lecturer = lecture.getLecturer().getName();
        this.lecturingDate = lectureItem.getLecturingDate();
        this.openSeats = lectureItem.getCapacity() - register.getCount();
    }
}
