//package com.hhplus.course.lecture;
//
//import com.hhplus.course.lecture.domain.Lecture;
//import com.hhplus.course.lecture.domain.LectureId;
//import com.hhplus.course.lecture.domain.LectureItem;
//import com.hhplus.course.lecture.domain.LectureRepository;
//import com.hhplus.course.lecture.infra.repository.LectureWithAvailableSeats;
//import com.hhplus.course.lecture.presentation.controller.LectureController;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class MemoryLectureRepository implements LectureRepository {
//    private final Map<LectureId, Lecture> map = new ConcurrentHashMap<>();
//
//    @Override
//    public Optional<Lecture> findById(LectureId lectureId) {
//        return Optional.ofNullable(map.get(lectureId));
//    }
//
//    @Override
//    public Optional<LectureItem> findByIdAndLectureItem_LecturingDate(LectureId lectureId, LocalDate lectureItemLecturingDate) {
//        return null;
//    }
//
//    @Override
//    public List<LectureWithAvailableSeats> findLectureWithAvailableSeats(LocalDate lecturingDate, Pageable pageable) {
//        return List.of();
//    }
//
//    @Override
//    public List<LectureController.AvailableLectureResponse> findByLectureItem_LecturingDate(LocalDate lectureItemLecturingDate, Pageable pageable) {
//        return List.of();
//    }
//
//
//    @Override
//    public Lecture save(Lecture lecture) {
//        return lecture;
//    }
//}
