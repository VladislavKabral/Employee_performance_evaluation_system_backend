package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user_status")
@Getter
@Setter
public class UserStatus {

    @Id
    @Column(name = "user_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_status_name")
    @NotEmpty(message = "Name of status user must be not empty")
    @Size(min = 2, max = 50, message = "Name of status user must be between 2 and 50 characters")
    private String name;

    @OneToMany(mappedBy = "status")
    @JsonManagedReference(value = "user_status")
    private List<User> users;

    public UserStatus() {
    }
}
