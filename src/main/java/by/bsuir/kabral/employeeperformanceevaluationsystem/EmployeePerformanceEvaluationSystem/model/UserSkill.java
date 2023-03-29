package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "UserSkills")
@Getter
@Setter
public class UserSkill {

    @Id
    @Column(name = "userSkillId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userSkillRate")
    private Double rate;

    @Column(name = "userSkillDate")
    private LocalDate date;

    public UserSkill() {
    }

    public UserSkill(Double rate, LocalDate date) {
        this.rate = rate;
        this.date = date;
    }
}
