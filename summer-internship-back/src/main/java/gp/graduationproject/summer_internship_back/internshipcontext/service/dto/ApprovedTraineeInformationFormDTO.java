package gp.graduationproject.summer_internship_back.internshipcontext.service.dto;

import java.time.LocalDate;
import java.util.List;

public class ApprovedTraineeInformationFormDTO {

    private Integer id;
    private String name;
    private String lastName;
    private String username;
    private LocalDate  datetime;
    private String position;
    private String type;
    private String code;
    private String semester;
    private String supervisorName;
    private String supervisorSurname;
    private Boolean healthInsurance;
    private Boolean insuranceApproval;
    private LocalDate  insuranceApprovalDate;
    private String status;
    private String companyUserName;
    private String branchName;
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;
    private String evaluateUserName;
    private String coordinatorUserName;
    private String evaluatingFacultyMember;
    private List<EvaluateFormDTO> evaluateForms;
    private List<ReportDTO> reports;
    private String country;
    private String city;
    private String district;
    private LocalDate internshipStartDate;
    private LocalDate internshipEndDate;
    private boolean inactive;

    public ApprovedTraineeInformationFormDTO(
            Integer id, String name, String lastName, String username, LocalDate datetime,
            String position, String type, String code, String semester,
            String supervisorName, String supervisorSurname, Boolean healthInsurance,
            Boolean insuranceApproval, LocalDate insuranceApprovalDate, // Instant → LocalDate
            String status, String companyUserName, String branchName,
            String companyAddress, String companyPhone, String companyEmail,
            String country, String city, String district,
            String coordinatorUserName, String evaluatingFacultyMember,
            LocalDate internshipStartDate, LocalDate internshipEndDate,
            List<EvaluateFormDTO> evaluateForms
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.datetime = datetime;
        this.position = position;
        this.type = type;
        this.code = code;
        this.semester = semester;
        this.supervisorName = supervisorName;
        this.supervisorSurname = supervisorSurname;
        this.healthInsurance = healthInsurance;
        this.insuranceApproval = insuranceApproval;
        this.insuranceApprovalDate = insuranceApprovalDate;
        this.status = status;
        this.companyUserName = companyUserName;
        this.branchName = branchName;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;
        this.companyEmail = companyEmail;
        this.country = country;
        this.city = city;
        this.district = district;
        this.coordinatorUserName = coordinatorUserName;
        this.evaluatingFacultyMember = evaluatingFacultyMember;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.evaluateForms = evaluateForms;
    }

    public ApprovedTraineeInformationFormDTO(
            Integer id, String name, String lastName, String username, LocalDate datetime,
            String position, String type, String code, String semester,
            String supervisorName, String supervisorSurname, Boolean healthInsurance,
            Boolean insuranceApproval, LocalDate insuranceApprovalDate,
            String status, String companyUserName, String branchName,
            String companyAddress, String companyPhone, String companyEmail,
            String country, String city, String district,
            String coordinatorUserName, String evaluatingFacultyMember,
            LocalDate internshipStartDate, LocalDate internshipEndDate
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.datetime = datetime;
        this.position = position;
        this.type = type;
        this.code = code;
        this.semester = semester;
        this.supervisorName = supervisorName;
        this.supervisorSurname = supervisorSurname;
        this.healthInsurance = healthInsurance;
        this.insuranceApproval = insuranceApproval;
        this.insuranceApprovalDate = insuranceApprovalDate;
        this.status = status;
        this.companyUserName = companyUserName;
        this.branchName = branchName;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;
        this.companyEmail = companyEmail;
        this.country = country;
        this.city = city;
        this.district = district;
        this.coordinatorUserName = coordinatorUserName;
        this.evaluatingFacultyMember = evaluatingFacultyMember;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
    }


    public ApprovedTraineeInformationFormDTO(
            Integer id, String name, String lastName, String username, LocalDate datetime,
            String position, String type, String code, String semester,
            String supervisorName, String supervisorSurname, Boolean healthInsurance,
            Boolean insuranceApproval, LocalDate insuranceApprovalDate,
            String status, String companyUserName, String branchName,
            String companyAddress, String companyPhone, String companyEmail,
            String country, String city, String district,
            String coordinatorUserName, String evaluatingFacultyMember,
            LocalDate internshipStartDate, LocalDate internshipEndDate,
            boolean inactive
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.datetime = datetime;
        this.position = position;
        this.type = type;
        this.code = code;
        this.semester = semester;
        this.supervisorName = supervisorName;
        this.supervisorSurname = supervisorSurname;
        this.healthInsurance = healthInsurance;
        this.insuranceApproval = insuranceApproval;
        this.insuranceApprovalDate = insuranceApprovalDate;
        this.status = status;
        this.companyUserName = companyUserName;
        this.branchName = branchName;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;
        this.companyEmail = companyEmail;
        this.country = country;
        this.city = city;
        this.district = district;
        this.coordinatorUserName = coordinatorUserName;
        this.evaluatingFacultyMember = evaluatingFacultyMember;
        this.internshipStartDate = internshipStartDate;
        this.internshipEndDate = internshipEndDate;
        this.inactive = inactive;
    }

    // Getters and Setters for all fields
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate  getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDate  datetime) {
        this.datetime = datetime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getSupervisorSurname() {
        return supervisorSurname;
    }

    public void setSupervisorSurname(String supervisorSurname) {
        this.supervisorSurname = supervisorSurname;
    }

    public Boolean getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(Boolean healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getEvaluateUserName() {
        return evaluateUserName;
    }

    public void setEvaluateUserName(String evaluateUserName) {
        this.evaluateUserName = evaluateUserName;
    }

    public List<EvaluateFormDTO> getEvaluateForms() {
        return evaluateForms;
    }

    public void setEvaluateForms(List<EvaluateFormDTO> evaluateForms) {
        this.evaluateForms = evaluateForms;
    }

    public List<ReportDTO> getReports() {
        return reports;
    }

    public void setReports(List<ReportDTO> reports) {
        this.reports = reports;
    }

    public String getCompanyUserName() {
        return companyUserName;
    }

    public void setCompanyUserName(String companyUserName) {
        this.companyUserName = companyUserName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Boolean getInsuranceApproval() {
        return insuranceApproval;
    }

    public void setInsuranceApproval(Boolean insuranceApproval) {
        this.insuranceApproval = insuranceApproval;
    }

    public LocalDate  getInsuranceApprovalDate() {
        return insuranceApprovalDate;
    }

    public void setInsuranceApprovalDate(LocalDate  insuranceApprovalDate) {
        this.insuranceApprovalDate = insuranceApprovalDate;
    }

    public String getCoordinatorUserName() {
        return coordinatorUserName;
    }

    public void setCoordinatorUserName(String coordinatorUserName) {
        this.coordinatorUserName = coordinatorUserName;
    }

    public String getEvaluatingFacultyMember() {
        return evaluatingFacultyMember;
    }

    public void setEvaluatingFacultyMember(String evaluatingFacultyMember) {
        this.evaluatingFacultyMember = evaluatingFacultyMember;
    }

    public LocalDate getInternshipStartDate() {
        return internshipStartDate;
    }

    public void setInternshipStartDate(LocalDate internshipStartDate) {
        this.internshipStartDate = internshipStartDate;
    }

    public LocalDate getInternshipEndDate() {
        return internshipEndDate;
    }

    public void setInternshipEndDate(LocalDate internshipEndDate) {
        this.internshipEndDate = internshipEndDate;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }
}