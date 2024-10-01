package com.hhplus.course.lecture.domain;

import com.hhplus.course.user.domain.UserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "lectures")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Lecture {
    @EmbeddedId
    private LectureId id;

    @Embedded
    private Lecturer lecturer;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureItem> lectureItem = new ArrayList<>();

    public static Lecture from(String id,
                               String lecturer,
                               List<LectureItem> lectureItems) {
        return Lecture.builder()
                .id(LectureId.of(id))
                .lecturer(Lecturer.of(lecturer))
                .lectureItem(lectureItems)
                .build();
    }

    public void join(UserId userId, LocalDate date) {
        LectureItem lectureItemOfDate = lectureItem.stream()
                .filter(x -> x.getLecturingDate().equals(date))
                .findFirst()
                .orElseThrow();

        if (LocalDate.now().isAfter(date)) {
            throw new IllegalStateException("이미 종료된 강좌를 수강할 수 없습니다.");
        }
        if (lectureItemOfDate.getStudentIds().size() >= 30) {
            throw new IllegalStateException("30명의 수강생 이상이 신청했습니다.");
        }
        if (lectureItemOfDate.getStudentIds().contains(userId)) {
            throw new IllegalStateException("중복 강좌를 수강할 수 없습니다.");
        }
        lectureItemOfDate.joinStudent(userId);
    }

    public boolean hasUserOf(UserId userId, LocalDate date) {
        LectureItem lectureItemOfDate = lectureItem.stream()
                .filter(li -> li.getLecturingDate().equals(date))
                .findFirst()
                .orElseThrow();
        return lectureItemOfDate.hasStudentOf(userId);
    }

    public LectureItem getLectureItem(LocalDate date) {
        return lectureItem.stream()
                .filter(li -> li.hasDateOf(date))
                .findFirst()
                .orElseThrow();
    }

}
