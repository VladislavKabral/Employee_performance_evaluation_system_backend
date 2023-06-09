package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SkillDTO {

    private int id;

    @NotEmpty(message = "Name of skill must be not empty")
    @Size(min = 2, max = 50, message = "Name of skill must be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Description of skill must be not empty")
    @Size(min = 4, max = 200, message = "Description of skill must be between 4 and 200 characters")
    private String description;

    public SkillDTO() {
    }
}
