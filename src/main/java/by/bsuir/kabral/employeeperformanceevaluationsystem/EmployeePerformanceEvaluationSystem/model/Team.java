package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Teams")
@Getter
@Setter
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "team_name")
    @NotEmpty(message = "Name of team must be not empty")
    @Size(min = 4, max = 50, message = "Name of team must be between 4 and 50 characters")
    private String name;

    @OneToMany(mappedBy = "team")
    @JsonManagedReference(value = "team_user")
    private List<User> users;

    public Team() {
    }
}
