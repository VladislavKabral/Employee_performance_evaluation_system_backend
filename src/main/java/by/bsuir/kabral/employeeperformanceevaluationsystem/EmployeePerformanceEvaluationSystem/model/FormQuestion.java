package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "forms_questions")
@Getter
@Setter
public class FormQuestion {

    @Id
    @Column(name = "forms_questions_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public FormQuestion() {
    }
}
