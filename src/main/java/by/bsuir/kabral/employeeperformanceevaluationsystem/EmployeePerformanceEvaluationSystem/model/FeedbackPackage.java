package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "FeedbackPackages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackPackage {

    @Id
    @Column(name = "packageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "packageName")
    @NotEmpty(message = "Name of package must be not empty")
    @Size(min = 4, max = 50, message = "Name of package must be between 4 and 50 characters")
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
}
