package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SalaryDTO {

    private int id;

    @NotEmpty(message = "Salary must be not empty")
    @Pattern(regexp = "^\\d+$", message = "Salary must contain only numbers")
    private String value;
}
