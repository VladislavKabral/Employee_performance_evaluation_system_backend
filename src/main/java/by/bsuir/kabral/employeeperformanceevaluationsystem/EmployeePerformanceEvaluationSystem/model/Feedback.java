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
    @Column(name = "feedbackId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "feedbackSourceUserId", referencedColumnName = "userId")
    private User sourceUser;

    @ManyToOne
    @JoinColumn(name = "feedbackStatusId", referencedColumnName = "feedbackStatusId")
    private FeedbackStatus status;

    @Column(name = "feedbackDate")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "feedbackPackageId", referencedColumnName = "packageId")
    private FeedbackPackage feedbackPackage;

    @OneToMany(mappedBy = "feedback")
    private List<Response> responses;

    public Feedback() {
    }

    public Feedback(User sourceUser, FeedbackStatus status, LocalDate date, FeedbackPackage feedbackPackage) {
        this.sourceUser = sourceUser;
        this.status = status;
        this.date = date;
        this.feedbackPackage = feedbackPackage;
    }
}
