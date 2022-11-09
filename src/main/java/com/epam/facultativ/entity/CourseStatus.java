package com.epam.facultativ.entity;

public enum CourseStatus {
    COMING_SOON("coming_soon"),
    IN_PROCESS("in_process"),
    COMPLETED("completed");

    private final String courseStatus;

    CourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseStatus() {
        return courseStatus;
    }
}
