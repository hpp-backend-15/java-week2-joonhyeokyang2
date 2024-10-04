package com.hhplus.course.lecture.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "lectures")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Lecture {
    @EmbeddedId
    private LectureId id;

    private String title;

    @Embedded
    private Lecturer lecturer;

    @ElementCollection
    @CollectionTable(name = "lectures_items", joinColumns = @JoinColumn(name = "lectures_id"))
    private List<LectureItem> lectureItems = new ArrayList<>();

    public static Lecture of(String id,
                             String title,
                             String lecturer,
                             List<LectureItem> lectureItems) {
        return Lecture.builder()
                .id(LectureId.of(id))
                .title(title)
                .lecturer(Lecturer.of(lecturer))
                .lectureItems(lectureItems)
                .build();
    }

}
