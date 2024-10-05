package com.hhplus.course.lecture.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

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

    @Column(name = "lectures_item_capacity")
    private final Integer capacity = 30;              // 요구사항에서 고정됨

    public static LectureItem of(String id, LocalDate lecturingDate) {
        return builder()
                .id(LectureItemId.of(id))
                .lecturingDate(lecturingDate)
                .build();
    }

}
