package com.hhplus.course.lecture.domain;

import com.hhplus.course.user.domain.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LectureTest {

    Lecture lecture;
    LocalDate now = LocalDate.now();

    @BeforeEach
    void setUp() {
        List<LectureItem> lectureItems = new ArrayList<>();
        lectureItems.add(LectureItem.of(LectureItemId.of("lectureItem1"), now, new ArrayList<>()));
        lecture = Lecture.from("lecture1", "heo jae", lectureItems);
    }


    @Test
    void 강의를_수강할수_있어야한다() throws Exception {
        //given
        UserId userId = UserId.of("user1");

        //when
        lecture.join(userId, now);

        //then
        assertThat(lecture.getLectureItem(now).getStudentIds()).isNotEmpty();
    }

    @Test
    void 강의의_수강생이30명이상이라면_수강실패한다() throws Exception {
        //given
        for (int i = 0; i < 30; i++) {
            UserId userId = UserId.of("user" + i);
            lecture.join(userId, now);
        }

        //when
        //then
        assertThatThrownBy(() -> lecture.join(UserId.of("user31"), now))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("30명의 수강생 이상이 신청했습니다.");

    }

    @Test
    void 강의의_수강생이중복됐다면_수강실패한다() throws Exception {
        //given
        UserId userId = UserId.of("user1");

        //when
        lecture.join(userId, now);

        //then
        assertThatThrownBy(() -> lecture.join(userId, now))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("중복 강좌를 수강할 수 없습니다.");

    }

    @Test
    void 수강시점이_강좌일을지났다면_수강실패한다() throws Exception {

        //given
        List<LectureItem> lectureItems = new ArrayList<>();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        lectureItems.add(LectureItem.of(LectureItemId.of("lectureItem1"), yesterday, new ArrayList<>()));
        lecture = Lecture.from("lecture1", "heo jae", lectureItems);
        UserId userId = UserId.of("user1");

        //when

        //then
        assertThatThrownBy(() -> lecture.join(userId, yesterday))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 종료된 강좌를 수강할 수 없습니다.");

    }

    @Test
    void 유저가_수강신청했다면_신청여부는_참이다() throws Exception {
        //given
        UserId userId = UserId.of("user1");

        //when
        lecture.join(userId, now);
        boolean hasUser = lecture.hasUserOf(userId, now);

        //then
        assertThat(hasUser).isTrue();

    }

    @Test
    void 유저가_수강신청하지않았다면_신청여부는_거짓이다() throws Exception {
        //given
        UserId userId = UserId.of("user1");

        //when
        boolean hasUser = lecture.hasUserOf(userId, now);

        //then
        assertThat(hasUser).isFalse();

    }
}
