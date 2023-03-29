package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    @Id
    @Column(name = "responseId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "responseRate")
    @Min(value = 0)
    private Double rate;

    @Column(name = "responseText")
    @NotEmpty(message = "Response content must be not empty")
    @Size(min = 10, max = 500, message = "Response content must be between 10 and 500 characters")
    private String text;

    @ManyToOne
    @JoinColumn(name = "responseQuestionId", referencedColumnName = "questionId")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "responseFeedbackId", referencedColumnName = "feedbackId")
    private Feedback feedback;
}
