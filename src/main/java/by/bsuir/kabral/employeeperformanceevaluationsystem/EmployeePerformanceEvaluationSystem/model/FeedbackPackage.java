package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "FeedbackPackages")
@Getter
@Setter
public class FeedbackPackage {

    @Id
    @Column(name = "packageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "packageName")
    private String name;

    @Column(name = "packageCreationDate")
    private LocalDate creationDate;

    @Column(name = "packageIsPublic")
    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "packageFormId", referencedColumnName = "formId")
    private Form form;

    @ManyToOne
    @JoinColumn(name = "packageTargetUserId", referencedColumnName = "userId")
    private User targetUser;

    @OneToMany(mappedBy = "feedbackPackage")
    private List<Feedback> feedbacks;

    public FeedbackPackage() {
    }

    public FeedbackPackage(String name, LocalDate creationDate, Boolean isPublic, Form form, User targetUser) {
        this.name = name;
        this.creationDate = creationDate;
        this.isPublic = isPublic;
        this.form = form;
        this.targetUser = targetUser;
    }
}
