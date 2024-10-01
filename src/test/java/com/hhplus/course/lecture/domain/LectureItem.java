package com.hhplus.course.lecture.domain;

import com.hhplus.course.user.domain.UserId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class LectureItem {

    @EmbeddedId
    @Column(name = "lectures_item_id")
    private LectureItemId id;

    @Column(name = "lectures_item_date")
    private LocalDate lecturingDate;

    @ElementCollection
    @CollectionTable(name = "lectures_item", joinColumns = @JoinColumn(name = "lectures_students_id"))
    @AttributeOverride(
            name = "studentIds",
            column = @Column(name = "lectures_item_students_id")
    )
    private List<UserId> studentIds = new ArrayList<>();

    public static LectureItem of(LectureItemId id, LocalDate lecturingDate, List<UserId> studentIds) {
        return builder()
                .id(id)
                .lecturingDate(lecturingDate)
                .studentIds(studentIds)
                .build();
    }

    void joinStudent(UserId studentId) {
        this.studentIds.add(studentId);
    }

    boolean hasStudentOf(UserId studentId) {
        return this.studentIds.contains(studentId);
    }

    boolean hasDateOf(LocalDate date) {
        return this.lecturingDate.equals(date);
    }
}
