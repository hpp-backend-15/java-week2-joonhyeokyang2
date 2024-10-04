package com.hhplus.course.lecture.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class LectureItemId {
    @Column(name = "lectures_item_id")
    String id;

    public static LectureItemId of(String id) {
        return new LectureItemId(id);}
}
