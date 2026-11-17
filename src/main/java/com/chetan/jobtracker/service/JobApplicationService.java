package com.chetan.jobtracker.service;

import com.chetan.jobtracker.dto.JobApplicationDTO.*;
import com.chetan.jobtracker.model.JobApplication;
import com.chetan.jobtracker.model.JobApplication.ApplicationStatus;
import com.chetan.jobtracker.model.User;
import com.chetan.jobtracker.repository.JobApplicationRepository;
import com.chetan.jobtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobRepo;

    @Autowired
    private UserRepository userRepository;

    // ─── Create ─────────────────────────────────────────────────
    public Response addApplication(Request request, String email) {
        User user = getUser(email);

        JobApplication app = JobApplication.builder()
                .companyName(request.getCompanyName())
                .jobRole(request.getJobRole())
                .status(request.getStatus())
                .jobUrl(request.getJobUrl())
                .location(request.getLocation())
                .notes(request.getNotes())
                .appliedDate(request.getAppliedDate())
                .user(user)
                .build();

        return toResponse(jobRepo.save(app));
    }

    // ─── Read All ────────────────────────────────────────────────
    public List<Response> getAllApplications(String email, String status, String company) {
        User user = getUser(email);
        List<JobApplication> apps;

        if (status != null && !status.isEmpty()) {
            // Filter by status
            ApplicationStatus appStatus = ApplicationStatus.valueOf(status.toUpperCase());
            apps = jobRepo.findByUserIdAndStatus(user.getId(), appStatus);
        } else if (company != null && !company.isEmpty()) {
            // Filter by company name
            apps = jobRepo.findByUserIdAndCompanyNameContainingIgnoreCase(user.getId(), company);
        } else {
            // Get all, ordered by date
            apps = jobRepo.findByUserIdOrderByAppliedDateDesc(user.getId());
        }

        return apps.stream().map(this::toResponse).collect(Collectors.toList());
    }

    // ─── Read One ────────────────────────────────────────────────
    public Response getApplicationById(Long id, String email) {
        User user = getUser(email);
        JobApplication app = jobRepo.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
        return toResponse(app);
    }

    // ─── Update ──────────────────────────────────────────────────
    public Response updateApplication(Long id, Request request, String email) {
        User user = getUser(email);
        JobApplication app = jobRepo.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        app.setCompanyName(request.getCompanyName());
        app.setJobRole(request.getJobRole());
        app.setStatus(request.getStatus());
        app.setJobUrl(request.getJobUrl());
        app.setLocation(request.getLocation());
        app.setNotes(request.getNotes());
        app.setAppliedDate(request.getAppliedDate());

        return toResponse(jobRepo.save(app));
    }

    // ─── Delete ──────────────────────────────────────────────────
    public void deleteApplication(Long id, String email) {
        User user = getUser(email);
        JobApplication app = jobRepo.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
        jobRepo.delete(app);
    }

    // ─── Stats ───────────────────────────────────────────────────
    public StatsResponse getStats(String email) {
        User user = getUser(email);
        Long userId = user.getId();

        long total = jobRepo.countByUserId(userId);
        long offers = jobRepo.countByUserIdAndStatus(userId, ApplicationStatus.OFFER_RECEIVED);

        StatsResponse stats = new StatsResponse();
        stats.setTotalApplications(total);
        stats.setApplied(jobRepo.countByUserIdAndStatus(userId, ApplicationStatus.APPLIED));
        stats.setInterviewScheduled(jobRepo.countByUserIdAndStatus(userId, ApplicationStatus.INTERVIEW_SCHEDULED));
        stats.setInterviewDone(jobRepo.countByUserIdAndStatus(userId, ApplicationStatus.INTERVIEW_DONE));
        stats.setOfferReceived(offers);
        stats.setRejected(jobRepo.countByUserIdAndStatus(userId, ApplicationStatus.REJECTED));
        stats.setWithdrawn(jobRepo.countByUserIdAndStatus(userId, ApplicationStatus.WITHDRAWN));
        stats.setSuccessRate(total > 0 ? (double) offers / total * 100 : 0.0);

        return stats;
    }

    // ─── Helpers ─────────────────────────────────────────────────
    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    private Response toResponse(JobApplication app) {
        Response res = new Response();
        res.setId(app.getId());
        res.setCompanyName(app.getCompanyName());
        res.setJobRole(app.getJobRole());
        res.setStatus(app.getStatus());
        res.setJobUrl(app.getJobUrl());
        res.setLocation(app.getLocation());
        res.setNotes(app.getNotes());
        res.setAppliedDate(app.getAppliedDate());
        res.setCreatedAt(app.getCreatedAt());
        res.setUpdatedAt(app.getUpdatedAt());
        return res;
    }
}
