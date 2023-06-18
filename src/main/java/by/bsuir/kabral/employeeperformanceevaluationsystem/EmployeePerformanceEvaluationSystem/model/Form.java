package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @JsonManagedReference(value = "package_form")
    private List<FeedbackPackage> feedbackPackages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "forms_questions",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Question> questions;

    public Form() {
    }
}
