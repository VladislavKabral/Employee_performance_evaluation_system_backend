package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @Column(name = "position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "position_name")
    @NotEmpty(message = "Name of position must be not empty")
    @Size(min = 4, max = 50, message = "Name of positions must be between 4 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name of position must be contain only letters")
    private String name;

    @OneToMany(mappedBy = "position")
    private List<User> users;

    public Position() {
    }
}
