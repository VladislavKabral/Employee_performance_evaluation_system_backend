package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Questions")
@Getter
@Setter
public class Question {

    @Id
    @Column(name = "questionId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "questionText")
    private String text;

    @ManyToOne
    @JoinColumn(name = "questionSkillId", referencedColumnName = "skillId")
    private Skill skill;

    @ManyToMany(mappedBy = "questions")
    private List<Form> forms;

    @OneToMany(mappedBy = "question")
    private List<Response> responses;

    public Question() {
    }

    public Question(String text, Skill skill) {
        this.text = text;
        this.skill = skill;
    }
}
