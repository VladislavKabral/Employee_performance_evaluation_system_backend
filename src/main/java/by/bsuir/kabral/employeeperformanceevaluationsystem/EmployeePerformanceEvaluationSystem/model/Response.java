package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Responses")
@Getter
@Setter
public class Response {

    @Id
    @Column(name = "responseId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "responseRate")
    private Double rate;

    @Column(name = "responseText")
    private String text;

    @ManyToOne
    @JoinColumn(name = "responseQuestionId", referencedColumnName = "questionId")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "responseFeedbackId", referencedColumnName = "feedbackId")
    private Feedback feedback;

    public Response() {
    }

    public Response(Double rate, String text, Question question, Feedback feedback) {
        this.rate = rate;
        this.text = text;
        this.question = question;
        this.feedback = feedback;
    }
}
