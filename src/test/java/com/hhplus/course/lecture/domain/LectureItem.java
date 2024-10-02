package com.hhplus.course.lecture.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Access(value = AccessType.FIELD)
public class LectureItem {
    @Embedded
    @Column(name = "lectures_item_id")
    private LectureItemId id;

    @Column(name = "lectures_item_date")
    private LocalDate lecturingDate;

    public static LectureItem of(LectureItemId id, LocalDate lecturingDate) {
        return builder()
                .id(id)
                .lecturingDate(lecturingDate)
                .build();
    }

}
