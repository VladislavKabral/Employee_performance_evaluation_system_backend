package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Positions")
@Getter
@Setter
public class Position {

    @Id
    @Column(name = "positionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "positionName")
    @Size(min = 4, max = 15, message = "Name of positions must be between 4 and 15 characters")
    private String name;

    @OneToMany(mappedBy = "position")
    private List<User> users;

    public Position() {
    }

    public Position(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }
}
