package com.hhplus.course.register.presentation;

import com.hhplus.course.register.application.RegisterService;
import com.hhplus.course.register.presentation.dto.ApplyRequest;
import com.hhplus.course.register.presentation.dto.ApplyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping

    public ResponseEntity<ApplyResponse> register(
            @RequestBody ApplyRequest request
    ) {
        ApplyResponse applyResponse = registerService.apply(request.userId(), request.lectureId(), request.date());
        return ResponseEntity.status(HttpStatus.CREATED).body(applyResponse);
    }

}
