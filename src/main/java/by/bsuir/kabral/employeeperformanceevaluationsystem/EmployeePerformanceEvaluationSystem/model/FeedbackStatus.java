package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "feedback_statuses")
@Getter
@Setter
public class FeedbackStatus {

    @Id
    @Column(name = "feedback_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "feedback_status_name")
    @NotEmpty(message = "Name of status feedback must be not empty")
    @Size(min = 2, max = 50, message = "Name of status feedback must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name of status feedback must be contain only letters")
    private String name;

    @OneToMany(mappedBy = "status")
    @JsonManagedReference(value = "feedback_status")
    private List<Feedback> feedbacks;

    public FeedbackStatus() {
    }
}
