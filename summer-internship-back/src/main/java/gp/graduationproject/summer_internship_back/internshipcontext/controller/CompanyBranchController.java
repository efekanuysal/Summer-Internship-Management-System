package gp.graduationproject.summer_internship_back.internshipcontext.controller;

import gp.graduationproject.summer_internship_back.internshipcontext.domain.CompanyBranch;
import gp.graduationproject.summer_internship_back.internshipcontext.domain.InactiveCompanyBranch;
import gp.graduationproject.summer_internship_back.internshipcontext.domain.User;
import gp.graduationproject.summer_internship_back.internshipcontext.repository.CompanyBranchRepository;
import gp.graduationproject.summer_internship_back.internshipcontext.repository.CompanyRepository;
import gp.graduationproject.summer_internship_back.internshipcontext.repository.InactiveCompanyBranchRepository;
import gp.graduationproject.summer_internship_back.internshipcontext.service.ApprovedTraineeInformationFormService;
import gp.graduationproject.summer_internship_back.internshipcontext.service.CompanyBranchService;
import gp.graduationproject.summer_internship_back.internshipcontext.service.PasswordResetTokenService;
import gp.graduationproject.summer_internship_back.internshipcontext.repository.UserRepository;
import gp.graduationproject.summer_internship_back.internshipcontext.domain.PasswordResetToken;
import gp.graduationproject.summer_internship_back.internshipcontext.service.dto.ApprovedTraineeInformationFormCompanyDTO;
import gp.graduationproject.summer_internship_back.internshipcontext.service.dto.ApprovedTraineeInformationFormDTO;
import gp.graduationproject.summer_internship_back.internshipcontext.service.dto.CompanyBranchDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/company-branch")

public class CompanyBranchController {

    private final CompanyBranchService companyBranchService;
    private final CompanyRepository companyRepository;
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserRepository userRepository;
    private final CompanyBranchRepository companyBranchRepository;
    private final InactiveCompanyBranchRepository inactiveCompanyBranchRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApprovedTraineeInformationFormService approvedTraineeInformationFormService;

    @Autowired
    public CompanyBranchController(CompanyBranchService companyBranchService,
                                   CompanyRepository companyRepository,
                                   PasswordResetTokenService passwordResetTokenService,
                                   UserRepository userRepository,
                                   CompanyBranchRepository companyBranchRepository,
                                   InactiveCompanyBranchRepository inactiveCompanyBranchRepository,
                                   ApprovedTraineeInformationFormService approvedTraineeInformationFormService) {
        this.companyBranchService = companyBranchService;
        this.companyRepository = companyRepository;
        this.passwordResetTokenService = passwordResetTokenService;
        this.userRepository = userRepository;
        this.companyBranchRepository = companyBranchRepository;
        this.inactiveCompanyBranchRepository = inactiveCompanyBranchRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.approvedTraineeInformationFormService = approvedTraineeInformationFormService;
    }

    /**
     * Retrieves all company branches associated with a specific company.
     *
     * @param username The username of the company
     * @return A list of company branches
     */
    @PostMapping
    public ResponseEntity<List<CompanyBranchDTO>> getCompanyBranches(@RequestBody String username) {
        List<CompanyBranch> companyBranches = companyBranchService.getAllCompanyBranchesofCompany(username);
        List<CompanyBranchDTO> companyBranchDTOS = companyBranches.stream()
                .map(branch -> new CompanyBranchDTO(branch.getBranchName()))
                .toList();
        return ResponseEntity.ok(companyBranchDTOS);
    }

    /**
     * Validates the password reset token.
     *
     * @param token The reset token
     * @return Success if valid, error if invalid or expired
     */
    @GetMapping("/validate-reset-token")
    public ResponseEntity<String> validateResetToken(@RequestParam String token) {
        Optional<PasswordResetToken> resetToken = passwordResetTokenService.validatePasswordResetToken(token);
        return resetToken.isPresent() ?
                ResponseEntity.ok("Token is valid.") :
                ResponseEntity.badRequest().body("Invalid or expired token.");
    }

    /**
     * Allows a company branch representative to set a new password.
     *
     * @param requestBody Contains reset token and new password
     * @return Success or error response
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        String newPassword = requestBody.get("newPassword");

        if (token == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Token and new password are required.");
        }

        return passwordResetTokenService.validatePasswordResetToken(token).map(resetToken -> {
            String username = resetToken.getUserName();
            Optional<User> userOptional = userRepository.findById(username);

            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found.");
            }

            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            passwordResetTokenService.deletePasswordResetToken(token);
            return ResponseEntity.ok("Password has been successfully reset.");
        }).orElse(ResponseEntity.badRequest().body("Invalid or expired token."));
    }
    @PostMapping("/add")
    @Transactional
    public ResponseEntity<String> addCompanyBranch(@RequestBody Map<String, String> payload) {
        String branchName = payload.get("branch_name");
        String address = payload.get("company_branch_address");
        String email = payload.get("company_branch_email");
        String phone = payload.get("company_branch_phone");
        String country = payload.get("company_branch_country");
        String city = payload.get("company_branch_city");
        String district = payload.get("company_branch_district");

        if (branchName == null || branchName.isEmpty()) {
            return ResponseEntity.status(400).body("Branch name cannot be empty");
        }

        CompanyBranch companyBranch = new CompanyBranch();
        companyBranch.setBranchName(branchName);
        companyBranch.setAddress(address);
        companyBranch.setBranchEmail(email);
        companyBranch.setPhone(phone);
        companyBranch.setCountry(country);
        companyBranch.setCity(city);
        companyBranch.setDistrict(district);

        companyBranchRepository.save(companyBranch);
        return ResponseEntity.status(201).body("Company branch added successfully");
    }

    /**
     * Checks whether a company branch is active or inactive.
     *
     * @param branchId The ID of the company branch.
     * @return ResponseEntity with status and message.
     */
    @GetMapping("/status/{branchId}")
    public ResponseEntity<String> checkBranchStatus(@PathVariable Integer branchId)
    {
        Optional<CompanyBranch> branch = companyBranchRepository.findById(branchId);

        if (branch.isEmpty())
        {
            return ResponseEntity.status(404).body("Company branch not found.");
        }

        boolean isInactive = inactiveCompanyBranchRepository.findByBranchId(branchId).isPresent();

        if (isInactive)
        {
            return ResponseEntity.status(200).body("Company branch is inactive.");
        }
        else
        {
            return ResponseEntity.status(200).body("Company branch is active.");
        }
    }


    /**
     * Resets the password of a company branch and sends the new password via email.
     *
     * @param requestBody Must contain "branch_user_name"
     * @return ResponseEntity with success or error message
     */
    @PostMapping("/send-password")
    public ResponseEntity<String> sendNewPasswordToCompanyBranch(@RequestBody Map<String, String> requestBody) {
        String branchUserName = requestBody.get("branch_user_name");

        if (branchUserName == null || branchUserName.isBlank()) {
            return ResponseEntity.badRequest().body("Branch username is required.");
        }

        Optional<User> userOptional = userRepository.findById(branchUserName);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Company branch user not found.");
        }

        Optional<CompanyBranch> companyBranchOptional = companyBranchRepository.findByBranchUserName(userOptional.get());
        if (companyBranchOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Company branch not found.");
        }

        String plainPassword = companyBranchService.generateRandomPassword();

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(plainPassword));
        userRepository.save(user);

        CompanyBranch companyBranch = companyBranchOptional.get();
        String email = companyBranch.getBranchEmail();
        companyBranchService.sendResetPasswordToCompanyBranch(email, user.getUserName(), plainPassword);

        return ResponseEntity.ok("New password has been sent to the company's email address.");
    }


    /**
     * Allows a logged-in company branch to change their password.
     *
     * @param requestBody Must contain "username", "newPassword", "confirmPassword"
     * @return ResponseEntity with success or error message
     */
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String newPassword = requestBody.get("newPassword");
        String confirmPassword = requestBody.get("confirmPassword");

        if (username == null || newPassword == null || confirmPassword == null) {
            return ResponseEntity.badRequest().body("All fields are required.");
        }

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match.");
        }

        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully.");
    }

    @GetMapping("/id-from-username/{username}")
    public ResponseEntity<Integer> getBranchIdFromUsername(@PathVariable String username) {
        return companyBranchService.getBranchIdByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a list of all approved internships for Student Affairs
     * using DTO projection for improved performance.
     *
     * @return List of approved internships in DTO format.
     */
    @GetMapping("/approvedInternships/{username}")
    public ResponseEntity<List<ApprovedTraineeInformationFormCompanyDTO>> getAllApprovedInternships(@PathVariable String username) {
        List<ApprovedTraineeInformationFormCompanyDTO> internshipDTOs =
                approvedTraineeInformationFormService.getApprovedInternshipDTOsForCompanies(username);
        return ResponseEntity.ok(internshipDTOs);
    }


    /**
     * Marks the company branch as active by removing it from the inactive list.
     *
     * @param branchId The ID of the company branch to verify
     * @return a message indicating the result
     */
    @PutMapping("/verify/{branchId}")
    @Transactional
    public ResponseEntity<String> verifyCompanyBranch(@PathVariable Integer branchId) {
        Optional<InactiveCompanyBranch> inactiveOpt = inactiveCompanyBranchRepository.findByBranchId(branchId);

        if (inactiveOpt.isPresent()) {
            inactiveCompanyBranchRepository.delete(inactiveOpt.get()); // 👈 doğrudan sil
            return ResponseEntity.ok("Company branch verified and marked as active.");
        } else {
            return ResponseEntity.ok("Company branch is already active.");
        }
    }




    /**
     * Checks if a given company branch is marked as inactive.
     *
     * @param branchId The ID of the company branch to check
     * @return true if the branch is inactive, false otherwise
     */
    @GetMapping("/isInactive/{branchId}")
    public ResponseEntity<Boolean> isInactive(@PathVariable Integer branchId) {
        boolean isInactive = inactiveCompanyBranchRepository.findByBranchId(branchId).isPresent();
        return ResponseEntity.ok(isInactive);
    }
}