package com.hhplus.course.register.application;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RegisterLockManager {
    private final  ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    public void lock(String lectureItemId) {
        ReentrantLock lock = lockMap.computeIfAbsent(lectureItemId, id -> new ReentrantLock());
        lock.lock();
    }

    public void unlock(String lectureItemId) {
        ReentrantLock lock = lockMap.get(lectureItemId);
        if (lock != null && lock.isHeldByCurrentThread()) lock.unlock();
    }

}
