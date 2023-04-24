package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TeamDTO {

    private int id;

    @NotEmpty(message = "Name of team must be not empty")
    @Size(min = 4, max = 50, message = "Name of team must be between 4 and 50 characters")
    private String name;

    private List<User> users;

    public TeamDTO() {
    }
}
