package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
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
