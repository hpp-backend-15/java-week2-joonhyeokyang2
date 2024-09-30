package com.hhplus.course.lecture.application.facade;

import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureRepository;
import com.hhplus.course.user.domain.UserId;
import com.hhplus.course.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
public class LectureFacadeIntTest {

    @Autowired
    LectureFacade lectureFacade;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    UserRepository userRepository;

    int numberOfThreads = 300;
    List<Integer> executionOrder;
    ExecutorService executorService;
    CountDownLatch latch; // 스레드 종료를 대기하기 위한 Latch

    @BeforeEach
    void setUp() {
        executorService = Executors.newFixedThreadPool(16);
        executionOrder = new ArrayList<>();
        latch = new CountDownLatch(numberOfThreads);
    }
    /**
     * 허재 코치 멘토링 (09.29, 토)
     * 파사드 -> 단위 테스트를 많이 짤까?
     *
     * 단위테스트 목적 = "자기 자신의 고유 로직" 을 타게팅해서 점검
     * 파사드 = "자기 자신의 고유 로직" 보다는 보통 "애그리게이션" 에 집중을 한다.
     *
     * => 즉, 애그리게이션 전용이고, 로직이 없으니까 테스트를 하지 않는다.
     * 서비스 -> 도메인 통합 테스트가 더 의미 있을 듯
     */

    //- 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
    //- 동일한 신청자는 동일한 강의에 대해서 한 번의 수강 신청만 성공할 수 있습니다.
    //- 특강은 선착순 30명만 신청 가능합니다.
    //- 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다.

    /*
        왜 lectureService.join(member) 인가?
        - 현재는 Member 도메인보다 lecture가 더 주요한 도메인이라고 생각했기 때문이다.
        - lectureService (facade) - lectureManager -> join을 위한 도메인 서비스
     */


    @Test
    void 수강신청자id로_수강신청이_가능하다() throws Exception {
        //given
        String lectureId = "lecture1";
        String userId = "user1";
        lectureRepository.save(Lecture.from("lecture1", LocalDate.now()));

        //when
        lectureFacade.enroll(lectureId, userId);

        //then
        Lecture lecture = lectureFacade.findById(LectureId.of("lecture1"));
        assertThat(lecture).isNotNull();
        assertThat(lecture.hasUserOf(UserId.of("user1"))).isTrue();
    }
}
