package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
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
    private String name;

    @Column(name = "skillDescription")
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
