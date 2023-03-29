package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userLastname")
    private String lastname;

    @Column(name = "userFirstname")
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
    private String email;

    @Column(name = "userHashPassword")
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

    public User() {
    }

    public User(String lastname, String firstname, Salary salary, Team team, Position position, String email, String hashPassword, User manager, UserStatus status) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.salary = salary;
        this.team = team;
        this.position = position;
        this.email = email;
        this.hashPassword = hashPassword;
        this.manager = manager;
        this.status = status;
    }
}
