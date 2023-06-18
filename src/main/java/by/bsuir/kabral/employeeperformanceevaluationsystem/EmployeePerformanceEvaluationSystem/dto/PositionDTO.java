package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionDTO {
    @NotEmpty(message = "Name of position must be not empty")
    @Size(min = 4, max = 50, message = "Name of positions must be between 4 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name of position must be contain only letters")
    private String name;

    public PositionDTO() {
    }
}
