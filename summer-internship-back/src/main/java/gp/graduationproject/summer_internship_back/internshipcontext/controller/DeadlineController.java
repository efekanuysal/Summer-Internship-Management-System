package gp.graduationproject.summer_internship_back.internshipcontext.controller;

import gp.graduationproject.summer_internship_back.internshipcontext.domain.Deadline;
import gp.graduationproject.summer_internship_back.internshipcontext.domain.User;
import gp.graduationproject.summer_internship_back.internshipcontext.repository.DeadlineRepository;
import gp.graduationproject.summer_internship_back.internshipcontext.repository.UserRepository;
import gp.graduationproject.summer_internship_back.internshipcontext.service.EmailService;
import gp.graduationproject.summer_internship_back.internshipcontext.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/api/deadlines")
public class DeadlineController {

    @Autowired
    private final DeadlineRepository deadlineRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;


    public DeadlineController(DeadlineRepository deadlineRepository, UserRepository userRepository, EmailService emailService) {
        this.deadlineRepository = deadlineRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
    // 📌 ✅ Deadline ekleme endpoint'i
    @PostMapping("/add")
    public ResponseEntity<?> addDeadline(@RequestBody Map<String, String> payload) {
        try {
            String internshipDeadlineStr = payload.get("internshipDeadline"); // ✅ opsiyonel
            String reportDeadlineStr = payload.get("reportDeadline"); // ✅ opsiyonel
            System.out.println("Mesajım: " + internshipDeadlineStr+ " "+ reportDeadlineStr);
            LocalDate internshipDeadline = internshipDeadlineStr != null && !internshipDeadlineStr.isEmpty()
                    ? LocalDate.parse(internshipDeadlineStr)
                    : null; // Eğer boşsa null ata

            LocalDate reportDeadline = reportDeadlineStr != null && !reportDeadlineStr.isEmpty()
                    ? LocalDate.parse(reportDeadlineStr)
                    : null; // Eğer boşsa null ata

            Optional<Deadline> existingDeadlineOpt = deadlineRepository.findById(1L);
            Deadline deadline;
            if (existingDeadlineOpt.isPresent()) {
                // Update existing row
                deadline = existingDeadlineOpt.get();
                if (internshipDeadline != null) deadline.setInternshipDeadline(internshipDeadline);
                if (reportDeadline != null) deadline.setReportDeadline(reportDeadline);
            } else {
                // Create new row if not exists
                deadline = new Deadline();
                deadline.setInternshipDeadline(internshipDeadline);
                deadline.setReportDeadline(reportDeadline);
            }
            List<User> allStudents = userRepository.findAllByUserType("Student");

            for (User student : allStudents) {
                if (student.getEmail() != null && !student.getEmail().isBlank()) {
                    String subject = "New Internship Deadline Updated!";
                    String body = "Dear " + student.getUserName() + ",\n\n"
                            + "New deadlines have been updated in the system.\n"
                            + (internshipDeadline != null ? "Internship Deadline: " + internshipDeadline + "\n" : "")
                            + (reportDeadline != null ? "Report Deadline: " + reportDeadline + "\n" : "")
                            + "\nBest regards,\nInternship Management System";
                    emailService.sendEmail(student.getEmail(), subject, body);
                }
            }

            deadlineRepository.save(deadline);



            return ResponseEntity.ok(Map.of("message", "Deadline updated successfully", "deadline", deadline));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while updating the deadline", "details", e.getMessage()));
        }
    }
    // Big L from Mert
    /*@GetMapping("/latestDeadline")
    public ResponseEntity<Map<String, Object>> getLatestDeadline() {
        Optional<Deadline> latestDeadlineOpt = deadlineRepository.findFirstByOrderByIdDesc();

        if (latestDeadlineOpt.isPresent()) {
            Deadline deadline = latestDeadlineOpt.get();
            LocalDate deadlineDate = deadline.getInternshipDeadline();
            LocalDate today = LocalDate.now();

            long daysRemaining = ChronoUnit.DAYS.between(today, deadlineDate);

            Map<String, Object> response = new HashMap<>();
            response.put("deadline", deadlineDate);
            response.put("daysRemaining", daysRemaining);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "No deadline found"));
        }
    }*/

    @GetMapping("/internship-deadline")
    public ResponseEntity<?> getInternshipDeadline() {
        Optional<Deadline> existingDeadlineOpt = deadlineRepository.findById(1L);

        LocalDate internshipDeadline = existingDeadlineOpt.map(Deadline::getInternshipDeadline).orElse(null);

        return ResponseEntity.ok(Collections.singletonMap("internshipDeadline", internshipDeadline)); // ✅ Works perfectly Mert should learn from it

    }

    @GetMapping("/report-deadline")
    public ResponseEntity<Map<String, LocalDate>> getReportDeadline() {
        Optional<Deadline> existingDeadlineOpt = deadlineRepository.findById(1L);

        LocalDate reportDeadline = existingDeadlineOpt.map(Deadline::getReportDeadline).orElse(null);

        return ResponseEntity.ok(Collections.singletonMap("reportDeadline", reportDeadline)); // ✅ Works perfectly Mert should learn from it
    }

    // Big L from Mert
    /*@PostMapping
    public ResponseEntity<String> setDeadline(@RequestBody Deadline deadline) {
        deadlineRepository.save(deadline);
        return ResponseEntity.ok("Deadlines saved successfully.");
    }*/

    @DeleteMapping("/delete/internship-deadline")
    public ResponseEntity<Map<String, String>> deleteInternshipDeadline() {
        Optional<Deadline> existingDeadlineOpt = deadlineRepository.findById(1L);

        if (existingDeadlineOpt.isPresent()) {
            Deadline deadline = existingDeadlineOpt.get();
            deadline.setInternshipDeadline(null); // ✅ Set internship deadline to null
            deadlineRepository.save(deadline); // ✅ Save changes
            return ResponseEntity.ok(Collections.singletonMap("message", "Internship deadline removed successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Deadline entry not found"));
        }
    }

    @DeleteMapping("/delete/report-deadline")
    public ResponseEntity<Map<String, String>> deleteReportDeadline() {
        Optional<Deadline> existingDeadlineOpt = deadlineRepository.findById(1L);

        if (existingDeadlineOpt.isPresent()) {
            Deadline deadline = existingDeadlineOpt.get();
            deadline.setReportDeadline(null); // ✅ Set report deadline to null
            deadlineRepository.save(deadline); // ✅ Save changes
            return ResponseEntity.ok(Collections.singletonMap("message", "Report deadline removed successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Deadline entry not found"));
        }
    }


}
