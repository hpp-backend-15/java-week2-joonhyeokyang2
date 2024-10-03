package com.hhplus.course.register.infra;

import com.hhplus.course.register.domain.Register;
import com.hhplus.course.register.domain.RegisterId;
import com.hhplus.course.register.domain.RegisterRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRegisterRepository extends RegisterRepository, JpaRepository<Register, RegisterId> {
}
