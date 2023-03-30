package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Skill;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {
    @NotEmpty(message = "Question content must be not empty")
    @Size(min = 10, max = 200, message = "Question content must be between 10 and 200 characters")
    private String text;

    private Skill skill;

    public QuestionDTO() {
    }
}
