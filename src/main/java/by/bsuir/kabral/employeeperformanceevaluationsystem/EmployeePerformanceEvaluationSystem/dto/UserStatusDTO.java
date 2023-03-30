package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserStatusDTO {
    @NotEmpty(message = "Name of status user must be not empty")
    @Size(min = 2, max = 50, message = "Name of status user must be between 2 and 50 characters")
    private String name;

    public UserStatusDTO() {
    }
}
