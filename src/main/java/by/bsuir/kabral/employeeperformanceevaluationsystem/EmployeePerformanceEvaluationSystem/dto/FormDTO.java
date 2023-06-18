package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Question;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormDTO {

    private int id;

    @NotEmpty(message = "Name of form must be not empty")
    @Size(min = 4, max = 50, message = "Name of form must be between 4 and 50 characters")
    private String name;

    private List<Question> questions;

    public FormDTO() {
    }
}
