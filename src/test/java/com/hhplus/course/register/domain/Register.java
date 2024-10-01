package com.hhplus.course.register.domain;

import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.lecture.domain.LectureItemId;
import com.hhplus.course.user.domain.User;
import com.hhplus.course.user.domain.UserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "registers")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Register {
    @EmbeddedId
    private RegisterId id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "registers_lecture_item_id"))
    private LectureItemId lectureItemId;

    @ElementCollection
    @CollectionTable(name = "registers_users", joinColumns = @JoinColumn(name = "registers_id"))
    private List<UserId> students = new ArrayList<>();

    public Register(LectureItem lectureItem, User user) {
        setLectureItem(lectureItem.getId());
        register(user);
    }

    private void setLectureItem(LectureItemId lectureItemId) {
        if (lectureItemId == null) throw new NullPointerException("lectureItem 없습니다");
        this.lectureItemId = lectureItemId;
    }

    public void register(User user) {
        validateOver30Criteria();
        validateDuplicatedUserCriteria(user);
        students.add(user.getId());
    }

    public boolean hasUserRegistered(User user) {
        return students.stream().anyMatch(userId -> userId.equals(user.getId()));
    }

    private void validateDuplicatedUserCriteria(User user) {
        students.stream().filter(x -> x.equals(user.getId())).findAny().ifPresent(student -> {
            throw new IllegalStateException("중복된 수강생 이미 Id가 존재합니다");
        });
    }

    private void validateOver30Criteria() {
        if (students.size() >= 30) {
            throw new IllegalStateException("30명 이상 수강 불가합니다.");
        }
    }
}
