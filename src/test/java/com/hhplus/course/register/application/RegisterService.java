package com.hhplus.course.register.application;

import com.hhplus.course.lecture.application.service.LectureService;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.register.domain.Register;
import com.hhplus.course.register.domain.RegisterId;
import com.hhplus.course.register.domain.RegisterRepository;
import com.hhplus.course.user.application.UserService;
import com.hhplus.course.user.domain.User;
import com.hhplus.course.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterService {
    private final LectureService lectureService;
    private final UserService userService;
    private final RegisterRepository registerRepository;

    @Transactional
    public ApplyResponse apply(String userId, String lectureId, LocalDate date) {
        LectureItem lectureItem = lectureService.findLectureItemByLectureIdAndDate(LectureId.of(lectureId), date);
        User user = userService.findById(UserId.of(userId));

        Register register = registerRepository.findByLectureItemIdAndStudentIdIn(lectureItem.getId(), user.getId())
                .orElse(new Register(lectureItem));

        register.register(user);

        registerRepository.save(register);
        return new ApplyResponse(userId, lectureId);
    }

    /*
        userId - 유저 상세 페이지 이동용
        lectureId - 강좌 페이지 이동용
     */
    public record ApplyResponse(
            String userId,
            String lectureId
    ) {
    }
}
