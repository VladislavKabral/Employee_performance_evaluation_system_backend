package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    private double averageFeedbackMark;

    private double bestAverageFeedbackMark;

    private double worstAverageFeedbackMark;

    private Skill bestSkill;

    private Skill worstSkill;

    private User bestFeedbackEmployee;

    private User worstFeedbackEmployee;

    private boolean isAllFeedbacksCompleted;
}
