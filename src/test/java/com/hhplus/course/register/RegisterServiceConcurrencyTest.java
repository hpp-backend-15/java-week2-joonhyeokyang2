package com.hhplus.course.register;

import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.lecture.domain.LectureRepository;
import com.hhplus.course.register.application.RegisterService;
import com.hhplus.course.register.domain.Register;
import com.hhplus.course.register.domain.RegisterRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yaml")
@SpringBootTest
public class RegisterServiceConcurrencyTest {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserService userService;

    LocalDate now;

    int numberOfThreads = 40;
    List<Integer> executionOrder;
    ExecutorService executorService;
    CountDownLatch latch; // 스레드 종료를 대기하기 위한 Latch

    @BeforeEach
    void setUp() {
        executorService = Executors.newFixedThreadPool(16);
        executionOrder = new ArrayList<>();
        latch = new CountDownLatch(numberOfThreads);
        now = LocalDate.now();
        LectureItem lectureItem1 = LectureItem.of("lectureItem1", now);
        LectureItem lectureItem2 = LectureItem.of("lectureItem2", now.plusDays(1));
        Lecture lecture1 = Lecture.of("lecture1", "TDD-딸각주도개발", "허재", List.of(lectureItem1, lectureItem2));
        Register register1 = new Register(LectureId.of("lecture1"), lectureItem1);
        Register register2 = new Register(LectureId.of("lecture1"), lectureItem2);
        registerRepository.save(register1);
        registerRepository.save(register2);
        for (int i = 0; i <= 41; i++) {
            userService.save(new User(UserId.of(String.valueOf(i))));
        }

        lectureRepository.save(lecture1);
    }

    @Test
    void IfConcurrent31UserApplyingThenFail() throws Exception {
        //given

        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger fail = new AtomicInteger(0);
        //when
        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    registerService.apply(String.valueOf(finalI), "lecture1", now);
                    success.incrementAndGet();
                } catch (Exception e) {
                    fail.incrementAndGet();
                    e.printStackTrace();
                } finally {
                    latch.countDown();  // 작업이 끝난 후 카운트 감소
                }
            });
        }

        latch.await();  // 모든 스레드가 끝날 때까지 대기

        //then
        Assertions.assertThat(success.get()).isEqualTo(30);
        Assertions.assertThat(fail.get()).isEqualTo(10);

    }


}