package com.hhplus.course.lecture.application.service;

import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.lecture.domain.LectureRepository;
import com.hhplus.course.lecture.infra.repository.LectureWithAvailableSeats;
import com.hhplus.course.lecture.presentation.controller.dto.AvailableLectureResponse;
import com.hhplus.course.lecture.presentation.controller.dto.LectureResponse;
import com.hhplus.course.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.hhplus.course.lecture.presentation.controller.dto.AvailableLectureResponse.AvailableLectureResponseBuilder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {
    private final LectureRepository lectureRepository;

    @Transactional
    public LectureItem findLectureItemByLectureIdAndDate(LectureId lectureId, LocalDate date) {
        return Optional.of(lectureRepository.findByIdAndLectureItems_LecturingDate(lectureId, date).get(0)).orElseThrow();
    }

    public List<AvailableLectureResponse> findAvailableLectures(LocalDate date) {
        List<LectureWithAvailableSeats> lectureWithAvailableSeats;
        if(date==null) lectureWithAvailableSeats = lectureRepository.findLectureWithAvailableSeats();
        else lectureWithAvailableSeats = lectureRepository.findLectureWithAvailableSeatsByDate(date);
        Map<String, AvailableLectureResponseBuilder> groupedLectures = new HashMap<>();

        for (LectureWithAvailableSeats lectureWithSeats : lectureWithAvailableSeats) {
            String lectureId = lectureWithSeats.getLectureId();

            // 기존에 존재하는 LectureId가 있다면, 그 객체를 가져와서 업데이트
            AvailableLectureResponseBuilder builder = groupedLectures
                    .computeIfAbsent(lectureId, id -> new AvailableLectureResponseBuilder(
                            lectureWithSeats.getTitle(),
                            lectureWithSeats.getLecturer()
                    ));

            // 날짜와 남은 좌석을 추가
            builder.addLecturingDate(lectureWithSeats.getLecturingDate(), lectureWithSeats.getOpenSeats());
        }

        // 최종 결과를 List로 변환
        List<AvailableLectureResponse> result = new ArrayList<>();
        for (AvailableLectureResponseBuilder builder : groupedLectures.values()) {
            result.add(builder.build());
        }
        return result;
    }

    public List<LectureResponse> findByUserId(String userId) {
        return lectureRepository.findByUserId(UserId.of(userId)).stream().map(LectureResponse::of).toList();
    }

}
