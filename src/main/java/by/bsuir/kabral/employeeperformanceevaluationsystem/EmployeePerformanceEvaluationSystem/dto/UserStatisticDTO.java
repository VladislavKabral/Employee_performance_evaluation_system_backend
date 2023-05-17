package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Skill;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticDTO {

    private double averageFeedbackMark;

    private double bestAverageFeedbackMark;

    private double worstAverageFeedbackMark;

    private Skill bestSkill;

    private Skill worstSkill;

    private User bestFeedbackEmployee;

    private User worstFeedbackEmployee;

    private Map<Double, Integer> distributionOfMarks;
}
