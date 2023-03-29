package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userLastname")
    @NotEmpty(message = "Lastname must be not empty")
    @Size(min = 2, max = 20, message = "Lastname must be between 2 and 20 characters")
    private String lastname;

    @Column(name = "userFirstname")
    @NotEmpty(message = "Firstname must be not empty")
    @Size(min = 2, max = 20, message = "Firstname must be between 2 and 20 characters")
    private String firstname;

    @ManyToOne
    @JoinColumn(name = "userSalaryId", referencedColumnName = "salaryId")
    private Salary salary;

    @ManyToOne
    @JoinColumn(name = "userTeamId", referencedColumnName = "teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "userPositionId", referencedColumnName = "positionId")
    private Position position;

    @Column(name = "userEmail")
    @Email
    @NotEmpty(message = "Email must be not empty")
    private String email;

    @Column(name = "userHashPassword")
    @NotEmpty(message = "Password must be not empty")
    private String hashPassword;

    @ManyToOne
    @JoinColumn(name = "userManagerId", referencedColumnName = "userId")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "userStatusId", referencedColumnName = "userStatusId")
    private UserStatus status;

    @OneToMany(mappedBy = "sourceUser")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "targetUser")
    private List<FeedbackPackage> packages;

    @ManyToMany(mappedBy = "users")
    private List<Skill> skills;
}
