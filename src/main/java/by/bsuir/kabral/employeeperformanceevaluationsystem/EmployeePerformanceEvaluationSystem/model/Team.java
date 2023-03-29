package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Teams")
@Getter
@Setter
public class Team {

    @Id
    @Column(name = "teamId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "teamName")
    private String name;

    @OneToMany(mappedBy = "team")
    private List<User> users;

    public Team() {
    }

    public Team(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }
}
