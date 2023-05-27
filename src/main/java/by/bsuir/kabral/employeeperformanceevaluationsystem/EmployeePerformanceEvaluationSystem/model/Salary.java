package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Salaries")
@Getter
@Setter
public class Salary {

    @Id
    @Column(name = "salary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "salary_value")
    @NotEmpty(message = "Salary must be not empty")
    @Pattern(regexp = "^\\d+$", message = "Salary must contain only numbers")
    private String value;

    @Column(name = "salary_date")
    private LocalDate date;

    @OneToMany(mappedBy = "salary")
    @JsonManagedReference(value = "salary_user")
    private List<User> users;

    public Salary() {
    }
}
