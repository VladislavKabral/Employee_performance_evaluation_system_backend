package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FormsQuestions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormQuestion {

    @Id
    @Column(name = "formsQuestionsId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "formsQuestionsIsRequired")
    private Boolean isRequired;
}
