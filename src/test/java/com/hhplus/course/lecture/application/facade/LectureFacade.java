package com.hhplus.course.lecture.application.facade;

import com.hhplus.course.lecture.application.service.LectureManager;
import com.hhplus.course.lecture.domain.Lecture;
import com.hhplus.course.lecture.domain.LectureId;
import com.hhplus.course.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureFacade {
    private final LectureManager lectureManager;


    public LectureEnrollResponse enroll(String lectureId, String userId) {
        /*
         * 수강 신청이 끝나면, 두 가지 경로가 존재한다.
         * 수강 상세 페이지 (lectureId 필요)
         * 수강 내역 확인을 위한 유저 개인 페이지 (userId 필요)
         */
        lectureManager.enroll(LectureId.of(lectureId), UserId.of(userId));
        return LectureEnrollResponse.of(lectureId, userId);
    }

    public Lecture findById(LectureId lectureId) {
        return lectureManager.findById(lectureId);
    }


    public record LectureEnrollResponse(
            String lectureId,
            String userId
    ) {
        public static LectureEnrollResponse of(String lectureId, String userId) {
            return new LectureEnrollResponse(lectureId, userId);
        }
    }
}
