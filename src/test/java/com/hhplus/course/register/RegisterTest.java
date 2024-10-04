package com.hhplus.course.register;

import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.register.domain.Register;
import com.hhplus.course.user.domain.User;
import com.hhplus.course.user.domain.UserId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RegisterTest {

    Lecture lecture;
    LectureItem lectureItem;
    User user = new User(UserId.of("user1"));
    LocalDate now = LocalDate.now();

    @BeforeEach
    void setUp() {
        List<LectureItem> lectureItems = new ArrayList<>();
        lectureItem = LectureItem.of("lectureItem1", now);
        lectureItems.add(lectureItem);
        lecture = Lecture.of("lecture1", "TDD - 딸깍 주도 개발", "heo jae", lectureItems);
    }


    @Test
    void 강의를_수강할수_있어야한다() throws Exception {
        //given

        //when
        Register register = new Register(lectureItem);
        register.register(user);

        //then
        assertThat(register).isNotNull();
        assertThat(register.getLectureItemId().getId()).isEqualTo("lectureItem1");
        Assertions.assertThat(register.getStudents()).isNotEmpty();
    }

    @Test
    void 강의신청시_수강생이한번에30명이상이라면_수강실패한다() throws Exception {
        //given
        Register register = new Register(lectureItem);
        for (int i = 0; i < 30; i++) {
            User newUser = new User(UserId.of("new user" + i));
            register.register(newUser);
        }

        //when

        //then
        assertThatThrownBy(() -> {
            register.register(new User(UserId.of("new user30")));
        })
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("30명 이상 수강 불가합니다.");

    }

    @Test
    void 강의신청시_수강생이중복됐다면_수강실패한다() throws Exception {
        //given
        Register register = new Register(lectureItem);
        User dupUser = new User(UserId.of("user1"));

        //when
        register.register(dupUser);

        //then
        assertThatThrownBy(() -> register.register(dupUser))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("중복된 수강생 이미 Id가 존재합니다");

    }

    @Test
    void 유저가_수강신청했다면_신청여부는_참이다() throws Exception {
        //given
        Register register = new Register(lectureItem);
        register.register(user);

        //when
        boolean hasUserRegistered = register.hasUserRegistered(user);

        //then
        assertThat(hasUserRegistered).isTrue();
    }

    @Test
    void 유저가_수강신청하지않았다면_신청여부는_거짓이다() throws Exception {
        //given
        Register register = new Register(lectureItem);
        User notReigsteredUser = new User(UserId.of("user1"));

        //when
        boolean hasUserRegistered = register.hasUserRegistered(notReigsteredUser);

        //then
        assertThat(hasUserRegistered).isFalse();

    }
}
