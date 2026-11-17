package com.chetan.jobtracker.controller;

import com.chetan.jobtracker.dto.JobApplicationDTO.*;
import com.chetan.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobService;

    // POST /api/applications  → Add new application
    @PostMapping
    public ResponseEntity<Response> addApplication(
            @Valid @RequestBody Request request,
            Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobService.addApplication(request, auth.getName()));
    }

    // GET /api/applications  → Get all (with optional filters)
    // ?status=APPLIED  or  ?company=Google
    @GetMapping
    public ResponseEntity<List<Response>> getAllApplications(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String company,
            Authentication auth) {
        return ResponseEntity.ok(jobService.getAllApplications(auth.getName(), status, company));
    }

    // GET /api/applications/{id}  → Get single application
    @GetMapping("/{id}")
    public ResponseEntity<Response> getApplicationById(
            @PathVariable Long id,
            Authentication auth) {
        return ResponseEntity.ok(jobService.getApplicationById(id, auth.getName()));
    }

    // PUT /api/applications/{id}  → Update application
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody Request request,
            Authentication auth) {
        return ResponseEntity.ok(jobService.updateApplication(id, request, auth.getName()));
    }

    // DELETE /api/applications/{id}  → Delete application
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(
            @PathVariable Long id,
            Authentication auth) {
        jobService.deleteApplication(id, auth.getName());
        return ResponseEntity.ok("Application deleted successfully");
    }

    // GET /api/applications/stats  → Get statistics
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(Authentication auth) {
        return ResponseEntity.ok(jobService.getStats(auth.getName()));
    }
}
