package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Managers")
@Getter
@Setter
public class Manager {

    @Id
    @Column(name = "managerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId")
    private int userId;

    @OneToMany(mappedBy = "manager")
    private List<User> users;

    public Manager() {
    }

    public Manager(int userId, List<User> users) {
        this.userId = userId;
        this.users = users;
    }
}
