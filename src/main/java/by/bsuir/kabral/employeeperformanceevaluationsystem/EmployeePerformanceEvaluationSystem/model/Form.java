package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Forms")
@Getter
@Setter
public class Form {

    @Id
    @Column(name = "form_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "form_name")
    @NotEmpty(message = "Name of form must be not empty")
    @Size(min = 4, max = 50, message = "Name of form must be between 4 and 50 characters")
    private String name;

    @OneToMany(mappedBy = "form")
    private List<FeedbackPackage> feedbackPackages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "forms_questions",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questions;

    public Form() {
    }
}
