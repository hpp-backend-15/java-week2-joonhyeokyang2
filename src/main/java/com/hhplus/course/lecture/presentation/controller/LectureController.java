package com.hhplus.course.lecture.presentation.controller;

import com.hhplus.course.lecture.application.service.LectureService;
import com.hhplus.course.lecture.presentation.controller.dto.AvailableLectureResponse;
import com.hhplus.course.lecture.presentation.controller.dto.LectureResponse;
import com.hhplus.course.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @GetMapping
    public ResponseEntity<List<AvailableLectureResponse>> searchLectureByDate(
            LocalDate date
    ) {
        return ResponseEntity.ok().body(lectureService.findAvailableLectures(date));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LectureResponse>> searchLectureByUserId(
            @PathVariable("userId") String userId
    ) {
        return ResponseEntity.ok().body(lectureService.findByUserId(userId));
    }

}
