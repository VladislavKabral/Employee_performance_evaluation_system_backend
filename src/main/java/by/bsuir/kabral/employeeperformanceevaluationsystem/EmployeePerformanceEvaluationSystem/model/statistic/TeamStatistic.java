package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.statistic;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Skill;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TeamStatistic {

    private static final int MAX_RATE = 10;

    private static final int MIN_RATE = 0;

    private double averageFeedbackMark;

    private double bestAverageFeedbackMark;

    private double worstAverageFeedbackMark;

    private Skill bestSkill;

    private Skill worstSkill;

    private User bestEmployee;

    private User worstEmployee;

    private Map<Double, Integer> distributionOfMarks;

    public TeamStatistic() {
        for (int i = MIN_RATE; i <= MAX_RATE; i++) {
            distributionOfMarks.put((double) i, 0);
        }
    }
}
