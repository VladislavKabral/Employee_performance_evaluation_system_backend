package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.*;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.statistic.TeamStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TeamStatisticService {

    private final UserStatisticService userStatisticService;

    private final ReportService reportService;

    @Autowired
    public TeamStatisticService(UserStatisticService userStatisticService, ReportService reportService) {
        this.userStatisticService = userStatisticService;
        this.reportService = reportService;
    }

    private List<FeedbackPackage> getAllPackages(List<User> employees) {
        List<FeedbackPackage> packages = new ArrayList<>();

        for (User employee: employees) {
            packages.addAll(employee.getPackages());
        }

        return packages;
    }

    private User findBestEmployee(List<User> employees) {
        User bestEmployee = employees.get(0);
        double averageMark = 0.0;

        for (User employee: employees) {
            double currentAverageMark = reportService.calculateAverageMark(userStatisticService
                    .findCompletedFeedbacks(employee.getPackages()));

            if (currentAverageMark > averageMark) {
                averageMark = currentAverageMark;
                bestEmployee = employee;
            }
        }

        return bestEmployee;
    }

    private User findWorstEmployee(List<User> employees) {
        User bestEmployee = employees.get(0);
        double averageMark = 0.0;

        for (User employee: employees) {
            double currentAverageMark = reportService.calculateAverageMark(userStatisticService
                    .findCompletedFeedbacks(employee.getPackages()));

            if (currentAverageMark < averageMark) {
                averageMark = currentAverageMark;
                bestEmployee = employee;
            }
        }

        return bestEmployee;
    }

    public TeamStatistic generateTeamStatistic(Team team) {
        TeamStatistic teamStatistic = new TeamStatistic();
        System.out.println("I am here.");
        List<Feedback> feedbacks = userStatisticService.findCompletedFeedbacks(getAllPackages(team.getUsers()));
        System.out.println("I am here.");

        double averageFeedbackMark = reportService.calculateAverageMark(feedbacks);
        double bestAverageFeedbackMark = reportService.calculateBestAverageFeedbackMark(feedbacks);
        double worstAverageFeedbackMark = reportService.calculateWorstAverageFeedbackMark(feedbacks);
        Skill bestSkill = reportService.calculateBestSkill(feedbacks);
        Skill worstSkill = reportService.calculateWorstSkill(feedbacks);
        User bestEmployee = findBestEmployee(team.getUsers());
        User worstEmployee = findWorstEmployee(team.getUsers());
        Map<Double, Integer> distributionOfMarks = userStatisticService.generateDistributionOfMarks(feedbacks);

        teamStatistic.setAverageFeedbackMark(averageFeedbackMark);
        teamStatistic.setBestAverageFeedbackMark(bestAverageFeedbackMark);
        teamStatistic.setWorstAverageFeedbackMark(worstAverageFeedbackMark);
        teamStatistic.setBestSkill(bestSkill);
        teamStatistic.setWorstSkill(worstSkill);
        teamStatistic.setBestEmployee(bestEmployee);
        teamStatistic.setWorstEmployee(worstEmployee);
        teamStatistic.setDistributionOfMarks(distributionOfMarks);

        return teamStatistic;
    }
}
