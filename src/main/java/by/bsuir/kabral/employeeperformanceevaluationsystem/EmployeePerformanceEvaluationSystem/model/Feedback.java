package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Feedbacks")
@Getter
@Setter
public class Feedback {

    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "feedback_source_user_id", referencedColumnName = "user_id")
    private User sourceUser;

    @ManyToOne
    @JoinColumn(name = "feedback_status_id", referencedColumnName = "feedback_status_id")
    private FeedbackStatus status;

    @Column(name = "feedback_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "feedback_package_id", referencedColumnName = "package_id")
    private FeedbackPackage feedbackPackage;

    @OneToMany(mappedBy = "feedback")
    private List<Response> responses;

    public Feedback() {
    }
}
