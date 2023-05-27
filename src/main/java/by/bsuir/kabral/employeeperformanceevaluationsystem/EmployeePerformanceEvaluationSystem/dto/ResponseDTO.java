package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Feedback;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Question;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    @Min(value = 0)
    private Double rate;

    @NotEmpty(message = "Response content must be not empty")
    @Size(min = 10, max = 500, message = "Response content must be between 10 and 500 characters")
    private String text;

    private Question question;

    private Feedback feedback;

    public ResponseDTO() {
    }
}
