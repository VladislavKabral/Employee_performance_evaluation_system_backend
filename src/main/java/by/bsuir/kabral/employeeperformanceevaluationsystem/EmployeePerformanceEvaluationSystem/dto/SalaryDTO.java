package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryDTO {
    @NotEmpty(message = "Salary must be not empty")
    @Pattern(regexp = "^\\d+$", message = "Salary must contain only numbers")
    private String value;

    public SalaryDTO() {
    }
}
