package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private int id;
    @NotEmpty(message = "Lastname must be not empty")
    @Size(min = 2, max = 20, message = "Lastname must be between 2 and 20 characters")
    private String lastname;

    @NotEmpty(message = "Firstname must be not empty")
    @Size(min = 2, max = 20, message = "Firstname must be between 2 and 20 characters")
    private String firstname;

    private Salary salary;

    private Position position;

    @Email(message = "Field Email must have email format")
    @NotEmpty(message = "Email must be not empty")
    private String email;

    @NotEmpty(message = "Password must be not empty")
    private String hashPassword;

    private Manager manager;

    public UserDTO() {
    }
}
