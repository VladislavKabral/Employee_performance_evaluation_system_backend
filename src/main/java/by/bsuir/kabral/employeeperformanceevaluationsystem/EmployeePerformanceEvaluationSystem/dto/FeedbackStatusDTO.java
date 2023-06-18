package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FeedbackStatusDTO {
    @NotEmpty(message = "Name of status feedback must be not empty")
    @Size(min = 2, max = 50, message = "Name of status feedback must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name of status feedback must be contain only letters")
    private String name;

    public FeedbackStatusDTO() {
    }
}
