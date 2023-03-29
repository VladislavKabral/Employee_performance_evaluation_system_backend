package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Feedbacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
