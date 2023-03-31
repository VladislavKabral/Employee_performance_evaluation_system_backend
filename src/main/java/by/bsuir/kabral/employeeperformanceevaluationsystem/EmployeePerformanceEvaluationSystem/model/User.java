package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_lastname")
    @NotEmpty(message = "Lastname must be not empty")
    @Size(min = 2, max = 20, message = "Lastname must be between 2 and 20 characters")
    private String lastname;

    @Column(name = "user_firstname")
    @NotEmpty(message = "Firstname must be not empty")
    @Size(min = 2, max = 20, message = "Firstname must be between 2 and 20 characters")
    private String firstname;

    @ManyToOne
    @JoinColumn(name = "user_salary_id", referencedColumnName = "salary_id")
    @JsonBackReference
    private Salary salary;

    @ManyToOne
    @JoinColumn(name = "user_team_id", referencedColumnName = "team_id")
    @JsonBackReference
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_position_id", referencedColumnName = "position_id")
    @JsonBackReference
    private Position position;

    @Column(name = "user_email")
    @Email
    @NotEmpty(message = "Email must be not empty")
    private String email;

    @Column(name = "user_hash_password")
    @NotEmpty(message = "Password must be not empty")
    private String hashPassword;

    @ManyToOne
    @JoinColumn(name = "user_manager_id", referencedColumnName = "user_id")
    @JsonBackReference
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "user_status_id", referencedColumnName = "user_status_id")
    @JsonBackReference
    private UserStatus status;

    @OneToMany(mappedBy = "sourceUser")
    @JsonManagedReference
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "targetUser")
    @JsonManagedReference
    private List<FeedbackPackage> packages;

    @ManyToMany(mappedBy = "users")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Skill> skills;

    public User() {
    }
}
