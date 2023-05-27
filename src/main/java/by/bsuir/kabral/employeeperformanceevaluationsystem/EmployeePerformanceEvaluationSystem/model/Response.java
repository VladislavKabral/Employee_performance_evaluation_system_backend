package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Responses")
@Getter
@Setter
public class Response {

    @Id
    @Column(name = "response_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "response_rate")
    @Min(value = 0)
    private Double rate;

    @Column(name = "response_text")
    @NotEmpty(message = "Response content must be not empty")
    @Size(min = 10, max = 500, message = "Response content must be between 10 and 500 characters")
    private String text;

    @ManyToOne
    @JoinColumn(name = "response_question_id", referencedColumnName = "question_id")
    @JsonBackReference(value = "question_response")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "response_feedback_id", referencedColumnName = "feedback_id")
    @JsonBackReference(value = "feedback_response")
    private Feedback feedback;

    public Response() {
    }
}
