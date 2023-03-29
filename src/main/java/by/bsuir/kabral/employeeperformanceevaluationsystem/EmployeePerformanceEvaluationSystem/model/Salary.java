package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Salaries")
@Getter
@Setter
public class Salary {

    @Id
    @Column(name = "salaryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "salaryValue")
    private String value;

    @Column(name = "salaryDate")
    private LocalDate date;

    @OneToMany(mappedBy = "salary")
    private List<User> users;

    public Salary() {
    }

    public Salary(String value, LocalDate date, List<User> users) {
        this.value = value;
        this.date = date;
        this.users = users;
    }
}
