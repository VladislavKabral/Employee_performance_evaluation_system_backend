package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Skill;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private double averageFeedbackMark;

    private double bestAverageFeedbackMark;

    private double worstAverageFeedbackMark;

    private Skill bestSkill;

    private Skill worstSkill;

    private User bestFeedbackEmployee;

    private User worstFeedbackEmployee;

    private boolean isAllFeedbacksCompleted;
}
