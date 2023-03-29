package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Question;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FormDTO {

    private int id;

    @NotEmpty(message = "Name of form must be not empty")
    @Size(min = 4, max = 50, message = "Name of form must be between 4 and 50 characters")
    private String name;

    private List<Question> questions;
}
