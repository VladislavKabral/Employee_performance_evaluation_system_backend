package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "Questions")
@Getter
@Setter
public class Question {

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "question_text")
    @NotEmpty(message = "Question content must be not empty")
    @Size(min = 10, max = 200, message = "Question content must be between 10 and 200 characters")
    private String text;

    @ManyToOne
    @JoinColumn(name = "question_skill_id", referencedColumnName = "skill_id")
    @JsonBackReference(value = "question_skill")
    private Skill skill;

    @ManyToMany(mappedBy = "questions")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<Form> forms;

    @OneToMany(mappedBy = "question")
    @JsonManagedReference(value = "question_response")
    private List<Response> responses;

    public Question() {
    }
}
