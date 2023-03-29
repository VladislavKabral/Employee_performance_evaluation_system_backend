package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Skills")
@Getter
@Setter
public class Skill {

    @Id
    @Column(name = "skillId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "skillName")
    @NotEmpty(message = "Name of skill must be not empty")
    @Size(min = 2, max = 50, message = "Name of skill must be between 2 and 50 characters")
    private String name;

    @Column(name = "skillDescription")
    @NotEmpty(message = "Description of skill must be not empty")
    @Size(min = 4, max = 200, message = "Description of skill must be between 4 and 200 characters")
    private String description;

    @OneToMany(mappedBy = "skill")
    private List<Question> questions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserSkills",
            joinColumns = @JoinColumn(name = "skillId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<User> users;

    public Skill() {
    }

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
