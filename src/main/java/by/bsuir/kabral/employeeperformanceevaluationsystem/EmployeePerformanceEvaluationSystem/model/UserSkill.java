package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user_skills")
@Getter
@Setter
public class UserSkill {

    @Id
    @Column(name = "user_skill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_skill_rate")
    @Min(value = 0)
    private Double rate;

    @Column(name = "user_skill_date")
    private LocalDate date;

    public UserSkill() {
    }
}
