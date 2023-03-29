package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FormsQuestions")
public class FormQuestion {

    @Id
    @Column(name = "formsQuestionsId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "formsQuestionsIsRequired")
    private Boolean isRequired;

    public FormQuestion() {
    }

    public FormQuestion(Boolean isRequired) {
        this.isRequired = isRequired;
    }
}
