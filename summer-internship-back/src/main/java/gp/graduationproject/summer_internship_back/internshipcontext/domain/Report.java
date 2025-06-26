package gp.graduationproject.summer_internship_back.internshipcontext.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "report", schema = "public")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_gen")
    @SequenceGenerator(name = "report_id_gen", sequenceName = "report_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "trainee_information_form_id", nullable = false)
    @JsonIgnore
    private ApprovedTraineeInformationForm traineeInformationForm;

    @Size(max = 10)
    @Column(name = "grade", nullable = false, length = 10)
    private String grade;

    @Column(name = "feedback", length = Integer.MAX_VALUE)
    private String feedback;

    @NotNull
    @Column(name = "file", nullable = false)
    private byte[] file;

    @Size(max = 55)
    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getInstructorFeedback() {
        return instructorFeedback;
    }

    public void setInstructorFeedback(String instructorFeedback) {
        this.instructorFeedback = instructorFeedback;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "instructor_feedback", columnDefinition = "TEXT")
    private String instructorFeedback;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public ApprovedTraineeInformationForm getTraineeInformationForm() {
        return traineeInformationForm;
    }
    public void setTraineeInformationForm(ApprovedTraineeInformationForm traineeInformationForm) {
        this.traineeInformationForm = traineeInformationForm;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    public byte[] getFile() {
        return file;
    }
    public void setFile(byte[] file) {
        this.file = file;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}