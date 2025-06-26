package gp.graduationproject.summer_internship_back.internshipcontext.service;

import gp.graduationproject.summer_internship_back.internshipcontext.domain.CompanyBranch;
import gp.graduationproject.summer_internship_back.internshipcontext.domain.InternshipOffer;
import gp.graduationproject.summer_internship_back.internshipcontext.domain.User;
import gp.graduationproject.summer_internship_back.internshipcontext.repository.CompanyBranchRepository;
import gp.graduationproject.summer_internship_back.internshipcontext.repository.InternshipOfferRepository;
import gp.graduationproject.summer_internship_back.internshipcontext.repository.UserRepository;
import gp.graduationproject.summer_internship_back.internshipcontext.service.dto.InternshipOfferCreateDTO;
import gp.graduationproject.summer_internship_back.internshipcontext.service.dto.InternshipOfferDTO;
import gp.graduationproject.summer_internship_back.internshipcontext.service.dto.InternshipOfferListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing internship offers.
 */
@Service
public class InternshipOfferService {

    private final InternshipOfferRepository internshipOfferRepository;
    private final CompanyBranchRepository companyBranchRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    /**
     * Constructor-based dependency injection.
     */
    @Autowired
    public InternshipOfferService(InternshipOfferRepository internshipOfferRepository,
                                  CompanyBranchRepository companyBranchRepository,
                                  UserRepository userRepository,
                                  EmailService emailService) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.companyBranchRepository = companyBranchRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    /**
     * Creates a new internship offer and sends notification to all students.
     *
     * @param dto the internship offer info from company
     */
    public void createInternshipOffer(InternshipOfferCreateDTO dto) {
        if (dto.getCompanyUserName() == null || dto.getCompanyUserName().isBlank()) {
            throw new RuntimeException("Company username is required.");
        }

        CompanyBranch branch = companyBranchRepository.findByBranchUserName_UserName(dto.getCompanyUserName())
                .orElseThrow(() -> new RuntimeException("Company Branch not found with username: " + dto.getCompanyUserName()));

        InternshipOffer offer = new InternshipOffer();
        offer.setCompanyBranch(branch);
        offer.setPosition(dto.getPosition());
        offer.setDepartment(dto.getDepartment());
        offer.setDetails(dto.getDetails());
        offer.setStartDate(dto.getStartDate());
        offer.setEndDate(dto.getEndDate());
        offer.setDescription(dto.getDescription());
        offer.setStatus("OPEN");

        internshipOfferRepository.save(offer);

        List<String> studentEmails = userRepository.findAllStudentEmails();
        emailService.sendNewOfferNotificationToAllStudents(studentEmails, offer);
    }


    /**
     * Retrieves all open internship offers and returns them as DTOs.
     *
     * @return List of open internship offers in DTO format.
     */
    public List<InternshipOfferListDTO> getAllOpenInternshipOffersAsDTO() {
        return internshipOfferRepository.findAllOpenOffersAsDTO();
    }

    /**
     * Get all offers created by a company branch user.
     */
    public List<InternshipOfferDTO> getCompanyInternshipOffers(String userName) {
        List<InternshipOffer> offers = internshipOfferRepository.findByCompanyBranch_BranchUserName_UserName(userName);
        return offers.stream()
                .map(InternshipOfferDTO::new)
                .collect(Collectors.toList());
    }


    /**
     * Updates an existing internship offer.
     *
     * @param offerId   ID of the offer to update.
     * @param position  New position title.
     * @param department New department.
     * @param details   Updated details.
     * @param startDate Updated start date.
     * @param endDate   Updated end date.
     * @param status    Updated status.
     */
    public void updateInternshipOffer(Integer offerId, String position, String department,
                                      String details, String startDate, String endDate, String status) {
        InternshipOffer offer = internshipOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Internship offer not found"));

        offer.setPosition(position);
        offer.setDepartment(department);
        offer.setDetails(details);
        offer.setStartDate(java.time.LocalDate.parse(startDate));
        offer.setEndDate(java.time.LocalDate.parse(endDate));
        offer.setStatus(status);

        internshipOfferRepository.save(offer);
    }

    /**
     * Deletes an internship offer by ID.
     *
     * @param offerId ID of the offer to delete.
     */
    public void deleteInternshipOffer(Integer offerId) {
        InternshipOffer offer = internshipOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Internship offer not found"));

        internshipOfferRepository.delete(offer);
    }

    public void createInternshipOfferWithImage(
            String position,
            String department,
            String details,
            LocalDate startDate,
            LocalDate endDate,
            String description,
            String companyUserName,
            MultipartFile imageFile
    ) {
        CompanyBranch branch = companyBranchRepository.findByBranchUserName_UserName(companyUserName)
                .orElseThrow(() -> new RuntimeException("Company Branch not found with username: " + companyUserName));

        InternshipOffer offer = new InternshipOffer();
        offer.setCompanyBranch(branch);
        offer.setPosition(position);
        offer.setDepartment(department);
        offer.setDetails(details);
        offer.setStartDate(startDate);
        offer.setEndDate(endDate);
        offer.setDescription(description);
        offer.setStatus("OPEN");


        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                offer.setImageData(imageFile.getBytes());
                offer.setImageType(imageFile.getContentType());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read image file", e);
            }
        }

        internshipOfferRepository.save(offer);

        List<String> studentEmails = userRepository.findAllStudentEmails();
        emailService.sendNewOfferNotificationToAllStudents(studentEmails, offer);
    }

    public InternshipOffer getOfferById(Integer offerId) {
        return internshipOfferRepository.findById(offerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found with ID: " + offerId));
    }
}