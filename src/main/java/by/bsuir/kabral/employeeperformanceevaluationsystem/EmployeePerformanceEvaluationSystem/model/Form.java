package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Forms")
@Getter
@Setter
public class Form {

    @Id
    @Column(name = "formId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "formName")
    private String name;

    @OneToMany(mappedBy = "form")
    private List<FeedbackPackage> feedbackPackages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "FormsQuestions",
            joinColumns = @JoinColumn(name = "formId"),
            inverseJoinColumns = @JoinColumn(name = "questionId"))
    private List<Question> questions;

    public Form() {
    }

    public Form(String name, List<FeedbackPackage> feedbackPackages) {
        this.name = name;
        this.feedbackPackages = feedbackPackages;
    }
}
