package com.chetan.jobtracker.dto;

import com.chetan.jobtracker.model.JobApplication.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JobApplicationDTO {

    @Data
    public static class Request {
        @NotBlank(message = "Company name is required")
        private String companyName;

        @NotBlank(message = "Job role is required")
        private String jobRole;

        @NotNull(message = "Status is required")
        private ApplicationStatus status;

        private String jobUrl;
        private String location;
        private String notes;
        private LocalDate appliedDate;
    }

    @Data
    public static class Response {
        private Long id;
        private String companyName;
        private String jobRole;
        private ApplicationStatus status;
        private String jobUrl;
        private String location;
        private String notes;
        private LocalDate appliedDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    public static class StatsResponse {
        private long totalApplications;
        private long applied;
        private long interviewScheduled;
        private long interviewDone;
        private long offerReceived;
        private long rejected;
        private long withdrawn;
        private double successRate; // offers / total * 100
    }
}
