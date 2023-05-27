package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "feedback_packages")
@Getter
@Setter
public class FeedbackPackage {

    @Id
    @Column(name = "package_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "package_name")
    @NotEmpty(message = "Name of package must be not empty")
    @Size(min = 4, max = 50, message = "Name of package must be between 4 and 50 characters")
    private String name;

    @Column(name = "package_creation_date")
    private LocalDate creationDate;

    @Column(name = "package_is_public")
    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "package_form_id", referencedColumnName = "form_id")
    @JsonBackReference(value = "package_form")
    private Form form;

    @ManyToOne
    @JoinColumn(name = "package_target_user_id", referencedColumnName = "user_id")
    @JsonBackReference(value = "package_target_user")
    private User targetUser;

    @OneToMany(mappedBy = "feedbackPackage")
    @JsonManagedReference(value = "feedback_package")
    private List<Feedback> feedbacks;

    public FeedbackPackage() {
    }
}
