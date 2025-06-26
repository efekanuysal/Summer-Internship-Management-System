package gp.graduationproject.summer_internship_back.internshipcontext.service.dto;

import java.time.LocalDate;

/**
 * DTO for listing open internship offers to students.
 */
public class InternshipOfferListDTO {

    private Integer offerId;
    private String position;
    private String department;
    private LocalDate startDate;
    private LocalDate endDate;
    private String companyBranchName;
    private String details;
    private String description;
    private Boolean hasImage;
    public InternshipOfferListDTO(Integer offerId, String position, String department,
                                  LocalDate startDate, LocalDate endDate,
                                  String companyBranchName, String details,
                                  String description, Boolean hasImage) {
        this.offerId = offerId;
        this.position = position;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
        this.companyBranchName = companyBranchName;
        this.details = details;
        this.description = description;
        this.hasImage = hasImage;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getCompanyBranchName() {
        return companyBranchName;
    }

    public String getDetails() {
        return details;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
}
