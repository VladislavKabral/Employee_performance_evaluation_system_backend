package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TeamDTO {
    @NotEmpty(message = "Name of team must be not empty")
    @Size(min = 4, max = 50, message = "Name of team must be between 4 and 50 characters")
    private String name;

    public TeamDTO() {
    }
}
