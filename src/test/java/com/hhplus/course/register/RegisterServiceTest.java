package com.hhplus.course.register;


import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.lecture.domain.LectureRepository;
import com.hhplus.course.register.application.RegisterService;
import com.hhplus.course.user.application.UserService;
import com.hhplus.course.user.domain.User;
import com.hhplus.course.user.domain.UserId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yaml")
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class RegisterServiceTest {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserService userService;

    LocalDate now;

    @BeforeEach
    public void setUp() {
        now = LocalDate.now();
        LectureItem lectureItem1 = LectureItem.of("lectureItem1", now);
        LectureItem lectureItem2 = LectureItem.of("lectureItem2", now.plusDays(1));
        Lecture lecture1 = Lecture.of("lecture1", "TDD-딸각주도개발", "허재", List.of(lectureItem1, lectureItem2));
        for (int i = 0; i <=31; i++) {
            userService.save(new User(UserId.of(String.valueOf(i))));
        }
        lectureRepository.save(lecture1);
    }

    @Test
    void IfNotConcurrent31UserApplyingThenFail() throws Exception {
        //given

        //when
        for (int i = 1; i <= 30; i++) {
            registerService.apply(String.valueOf(i), "lecture1", now);
        }


        //then
        Assertions.assertThatThrownBy(() ->
                        registerService.apply("31", "lecture1", now))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("30명 이상 수강 불가합니다.");

    }
}
