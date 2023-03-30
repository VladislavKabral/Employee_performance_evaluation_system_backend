package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSkillDTO {
    @Min(value = 0)
    private Double rate;

    public UserSkillDTO() {
    }
}
