package com.hhplus.course.lecture.domain;

import com.hhplus.course.lecture.infra.repository.LectureWithAvailableSeats;
import com.hhplus.course.user.domain.UserId;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LectureRepository {
    @Query("select new com.hhplus.course.lecture.infra.repository.LectureWithAvailableSeats(l, li, r) " +
            "from Lecture l join l.lectureItems li join Register r on li.id = r.lectureItemId " +
            "where r.count < 30 " +
            "and (coalesce(:date, null) is null or li.lecturingDate = :date) ")
    List<LectureWithAvailableSeats> findLectureWithAvailableSeats(@Param("date") LocalDate date);

    @Query("select li " +
            "from Lecture l join l.lectureItems li " +
            "where l.id = :lectureId and li.lecturingDate = :date")
    List<LectureItem> findByIdAndLectureItems_LecturingDate(
            @Param("lectureId") LectureId lectureId,
            @Param("date") LocalDate date);

    @Query("select l " +
            "from Lecture l " +
            "join l.lectureItems li " +
            "join Register r on li.id = r.lectureItemId " +
            "join r.students s " +
            "where s = :userId")
    List<Lecture> findByUserId(@Param("userId") UserId userId);

    Lecture save(Lecture lecture);

}
