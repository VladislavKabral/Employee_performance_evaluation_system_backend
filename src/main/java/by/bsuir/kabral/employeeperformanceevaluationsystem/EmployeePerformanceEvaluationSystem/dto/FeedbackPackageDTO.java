package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Form;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackPackageDTO {

    private int id;
    @NotEmpty(message = "Name of package must be not empty")
    @Size(min = 4, max = 50, message = "Name of package must be between 4 and 50 characters")
    private String name;

    private Boolean isPublic;
	
    private Form form;

    private User targetUser;

    public FeedbackPackageDTO() {
    }
}
