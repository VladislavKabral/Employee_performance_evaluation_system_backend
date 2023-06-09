package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @JsonManagedReference(value = "position_user")
    private List<User> users;

    public Position() {
    }
}
