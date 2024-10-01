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
public class Lecturer {

    @Column(name = "lectures_lecturer")
    private String name;

    public static Lecturer of(String name) {
        return new Lecturer(name);
    }
}
