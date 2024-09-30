package com.hhplus.course.lecture.domain;

import com.hhplus.course.user.domain.UserId;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    private LocalDate lecturingDate;

    @Getter
    @ElementCollection
    @BatchSize(size = 30)
    private final List<UserId> studentIds = new ArrayList<>();

    public void join(UserId userId) {
        if (LocalDate.now().isAfter(lecturingDate)) {
            throw new IllegalStateException("이미 종료된 강좌를 수강할 수 없습니다.");
        }
        if (studentIds.size() >= 30) {
            throw new IllegalStateException("30명의 수강생 이상이 신청했습니다.");
        }
        if (studentIds.contains(userId)) {
            throw new IllegalStateException("중복 강좌를 수강할 수 없습니다.");
        }
        studentIds.add(userId);
    }

    public static Lecture from(String id, LocalDate lecturingDate) {
        return Lecture.builder()
                .id(LectureId.of(id))
                .lecturingDate(lecturingDate)
                .build();
    }

    public boolean hasUserOf(UserId userId) {
        return studentIds.contains(userId);
    }
}
