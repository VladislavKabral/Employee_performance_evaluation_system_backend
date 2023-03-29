package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @Column(name = "questionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "questionText")
    @NotEmpty(message = "Question content must be not empty")
    @Size(min = 10, max = 200, message = "Question content must be between 10 and 200 characters")
    private String text;

    @ManyToOne
    @JoinColumn(name = "questionSkillId", referencedColumnName = "skillId")
    private Skill skill;

    @ManyToMany(mappedBy = "questions")
    private List<Form> forms;

    @OneToMany(mappedBy = "question")
    private List<Response> responses;
}
