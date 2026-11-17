package com.chetan.jobtracker.repository;

import com.chetan.jobtracker.model.JobApplication;
import com.chetan.jobtracker.model.JobApplication.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // Get all applications for a user
    List<JobApplication> findByUserId(Long userId);

    // Filter by status
    List<JobApplication> findByUserIdAndStatus(Long userId, ApplicationStatus status);

    // Filter by company name (case-insensitive)
    List<JobApplication> findByUserIdAndCompanyNameContainingIgnoreCase(Long userId, String companyName);

    // Find single application belonging to user (security check)
    Optional<JobApplication> findByIdAndUserId(Long id, Long userId);

    // Count by status for stats
    long countByUserIdAndStatus(Long userId, ApplicationStatus status);

    // Total count for a user
    long countByUserId(Long userId);

    // Get recent applications ordered by date
    List<JobApplication> findByUserIdOrderByAppliedDateDesc(Long userId);
}
