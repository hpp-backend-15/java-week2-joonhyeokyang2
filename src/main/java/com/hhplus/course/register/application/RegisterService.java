package com.hhplus.course.register.application;

import com.hhplus.course.lecture.application.service.LectureService;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.lecture.domain.LectureItem;
import com.hhplus.course.register.domain.Register;
import com.hhplus.course.register.domain.RegisterRepository;
import com.hhplus.course.register.presentation.dto.ApplyResponse;
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
    public synchronized ApplyResponse apply(String userId, String lectureId, LocalDate date) {
        LectureItem lectureItem = lectureService.findLectureItemByLectureIdAndDate(LectureId.of(lectureId), date);
        User user = userService.findById(UserId.of(userId));

        Register register = registerRepository.findByLectureItemId(lectureItem.getId())
                .orElse(new Register(LectureId.of(lectureId), lectureItem));

        register.register(user);

        registerRepository.save(register);
        return new ApplyResponse(userId, lectureId);
    }


}