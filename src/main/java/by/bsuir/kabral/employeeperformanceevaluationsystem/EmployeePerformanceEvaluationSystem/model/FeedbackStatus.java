package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
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
