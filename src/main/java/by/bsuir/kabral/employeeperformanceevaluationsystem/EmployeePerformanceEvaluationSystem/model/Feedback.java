package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference(value = "user_feedback")
    private User sourceUser;

    @ManyToOne
    @JoinColumn(name = "feedback_status_id", referencedColumnName = "feedback_status_id")
    @JsonBackReference(value = "feedback_status")
    private FeedbackStatus status;

    @Column(name = "feedback_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "feedback_package_id", referencedColumnName = "package_id")
    @JsonBackReference(value = "feedback_package")
    private FeedbackPackage feedbackPackage;

    @OneToMany(mappedBy = "feedback")
    @JsonManagedReference(value = "feedback_response")
    private List<Response> responses;

    public Feedback() {
    }
}
