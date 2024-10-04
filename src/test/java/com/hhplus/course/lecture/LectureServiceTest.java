package com.hhplus.course.lecture;

import com.hhplus.course.lecture.application.service.LectureService;
import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.lecture.domain.LectureRepository;
import com.hhplus.course.lecture.presentation.controller.dto.AvailableLectureResponse;
import com.hhplus.course.lecture.presentation.controller.dto.LectureResponse;
import com.hhplus.course.register.application.RegisterService;
import com.hhplus.course.register.domain.Register;
import com.hhplus.course.user.domain.User;
import com.hhplus.course.user.domain.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yaml")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class LectureServiceTest {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureRepository lectureRepository;

    LocalDate now;

    @BeforeEach
    void setUp() {
        now = LocalDate.now();
        LectureItem lectureItem1 = LectureItem.of("lectureItem1", now);
        LectureItem lectureItem2 = LectureItem.of("lectureItem2", now.plusDays(1));
        Lecture lecture1 = Lecture.of("lecture1", "TDD-딸각주도개발", "허재", List.of(lectureItem1, lectureItem2));
        lectureRepository.save(lecture1);
    }


    @Test
    @DisplayName("lectureId와 날짜로 강의 항목을 찾을 수 있다.")
    void findLectureItemByLectureIdAndDate() {
        //given

        //when
        LectureItem lectureItem = lectureService.findLectureItemByLectureIdAndDate(LectureId.of("lecture1"), now);

        //then
        assertThat(lectureItem).isNotNull();
    }

    @Test
    @DisplayName("날짜별로 신청 가능한 강의 항목을 찾을 수 있다.")
    void findAvailableLectures() {
        List<AvailableLectureResponse> availableLectures = lectureService.findAvailableLectures(now);

        assertThat(availableLectures).isNotNull();
        assertThat(availableLectures).size().isEqualTo(1);
        assertThat(availableLectures.get(0).title()).isEqualTo("TDD-딸각주도개발");
    }

    @Test
    @DisplayName("(빈 값) 날짜별로 신청 가능한 강의 항목을 찾을 수 있다.")
    void findAvailableLecturesWithNull() {
        //given, when
        List<AvailableLectureResponse> availableLectures = lectureService.findAvailableLectures(null);

        assertThat(availableLectures).isNotNull();
        assertThat(availableLectures.size()).isEqualTo(1);
        assertThat(availableLectures.get(0).title()).isEqualTo("TDD-딸각주도개발");
        assertThat(availableLectures.get(0).lecturingDatesAndOpenSeats().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("아무도 신청하지 않은 강의의 수강생은 없다.")
    void findByUserIdIfNull() {
        // given, when
        List<LectureResponse> responseList = lectureService.findByUserId("100");

        // then
        assertThat(responseList.size()).isEqualTo(0);
    }



}
