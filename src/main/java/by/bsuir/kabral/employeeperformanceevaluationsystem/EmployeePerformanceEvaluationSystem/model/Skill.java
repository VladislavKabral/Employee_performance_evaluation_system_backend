package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @Column(name = "skill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "skill_name")
    @NotEmpty(message = "Name of skill must be not empty")
    @Size(min = 2, max = 50, message = "Name of skill must be between 2 and 50 characters")
    private String name;

    @Column(name = "skill_description")
    @NotEmpty(message = "Description of skill must be not empty")
    @Size(min = 4, max = 200, message = "Description of skill must be between 4 and 200 characters")
    private String description;

    @OneToMany(mappedBy = "skill")
    @JsonManagedReference
    private List<Question> questions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_skills",
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<User> users;

    public Skill() {
    }
}
