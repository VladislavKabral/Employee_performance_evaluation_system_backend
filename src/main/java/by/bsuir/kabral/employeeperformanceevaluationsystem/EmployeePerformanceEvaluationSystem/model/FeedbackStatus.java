package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "FeedbackStatuses")
@Getter
@Setter
public class FeedbackStatus {

    @Id
    @Column(name = "feedbackStatusId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "feedbackStatusName")
    @Size(min = 2, max = 15, message = "Name of status feedback must be between 2 and 15 characters")
    private String name;

    @OneToMany(mappedBy = "status")
    private List<Feedback> feedbacks;

    public FeedbackStatus() {
    }

    public FeedbackStatus(String name, List<Feedback> feedbacks) {
        this.name = name;
        this.feedbacks = feedbacks;
    }
}
