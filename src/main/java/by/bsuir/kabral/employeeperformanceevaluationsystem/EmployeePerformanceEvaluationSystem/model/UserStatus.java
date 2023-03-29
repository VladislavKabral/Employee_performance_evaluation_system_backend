package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "UserStatus")
@Getter
@Setter
public class UserStatus {

    @Id
    @Column(name = "userStatusId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userStatusName")
    @NotEmpty(message = "Name of status user must be not empty")
    @Size(min = 2, max = 50, message = "Name of status user must be between 2 and 50 characters")
    private String name;

    @OneToMany(mappedBy = "status")
    private List<User> users;

    public UserStatus() {
    }

    public UserStatus(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }
}
