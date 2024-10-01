package com.hhplus.course.lecture.presentation.controller;

import com.hhplus.course.lecture.application.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    //TODO 날짜별 강의 조회 기능
}
