package com.hhplus.course.lecture.presentation.controller;

import com.hhplus.course.lecture.application.facade.LectureFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.hhplus.course.lecture.application.facade.LectureFacade.LectureEnrollResponse;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    @PostMapping
    public ResponseEntity<LectureEnrollResponse> enroll(@RequestBody LectureEnrollRequest lectureEnrollRequest) {
        String lectureId = lectureEnrollRequest.lectureId();
        String userId = lectureEnrollRequest.userId();
        LocalDate date = lectureEnrollRequest.date();
        LectureEnrollResponse enrollResponse = lectureFacade.enroll(lectureId, userId, date);

        return ResponseEntity.status(CREATED).body(enrollResponse);
    }
}
