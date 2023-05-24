package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.*;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.statistic.UserStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserStatisticService {

    private static final String FEEDBACK_STATUS_COMPLETED = "COMPLETED";

    private static final int MAX_RATE = 10;

    private static final int MIN_RATE = 0;

    private final ReportService reportService;

    @Autowired
    public UserStatisticService(ReportService reportService) {
        this.reportService = reportService;
    }

    public List<Feedback> findCompletedFeedbacks(List<FeedbackPackage> packages) {
        List<Feedback> feedbacks = new ArrayList<>();

        for (FeedbackPackage feedbackPackage: packages) {
            List<Feedback> completedFeedbacks = feedbackPackage.getFeedbacks()
                    .stream()
                    .filter(feedback -> Objects.equals(feedback.getStatus().getName(), FEEDBACK_STATUS_COMPLETED))
                    .toList();

            feedbacks.addAll(completedFeedbacks);
        }

        return feedbacks;
    }

    public Map<Double, Integer> generateDistributionOfMarks(List<Feedback> feedbacks) {
        Map<Double, Integer> distributionOfMarks = initializeDistributionOfMarksMap();

        for (Feedback feedback: feedbacks) {
            for (Response response: feedback.getResponses()) {
                distributionOfMarks.put(response.getRate(),
                        distributionOfMarks.get(response.getRate()) + 1);
            }
        }

        return distributionOfMarks;
    }

    public Map<Double, Integer> initializeDistributionOfMarksMap() {
        Map<Double, Integer> distributionOfMarks = new HashMap<>();

        for (int i = MIN_RATE; i <= MAX_RATE; i++) {
            distributionOfMarks.put((double) i, 0);
        }

        return distributionOfMarks;
    }

    public UserStatistic generateUserStatistic(User user) {
        UserStatistic userStatistic = new UserStatistic();
        List<Feedback> feedbacks = findCompletedFeedbacks(user.getPackages());

        double averageFeedbackMark = reportService.calculateAverageMark(feedbacks);
        double bestAverageFeedbackMark = reportService.calculateBestAverageFeedbackMark(feedbacks);
        double worstAverageFeedbackMark = reportService.calculateWorstAverageFeedbackMark(feedbacks);
        Skill bestSkill = reportService.calculateBestSkill(feedbacks);
        Skill worstSkill = reportService.calculateWorstSkill(feedbacks);
        User bestFeedbackEmployee = reportService.findBestFeedbackEmployee(feedbacks);
        User worstFeedbackEmployee = reportService.findWorstFeedbackEmployee(feedbacks);
        Map<Double, Integer> distributionOfMarks = generateDistributionOfMarks(feedbacks);

        userStatistic.setAverageFeedbackMark(averageFeedbackMark);
        userStatistic.setBestAverageFeedbackMark(bestAverageFeedbackMark);
        userStatistic.setWorstAverageFeedbackMark(worstAverageFeedbackMark);
        userStatistic.setBestSkill(bestSkill);
        userStatistic.setWorstSkill(worstSkill);
        userStatistic.setBestFeedbackEmployee(bestFeedbackEmployee);
        userStatistic.setWorstFeedbackEmployee(worstFeedbackEmployee);
        userStatistic.setDistributionOfMarks(distributionOfMarks);

        return userStatistic;
    }
}
