package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReportService {

    private static final String FEEDBACK_STATUS_COMPLETED = "COMPLETED";

    public Report generateReport(FeedbackPackage feedbackPackage) {
        Report report = new Report();

        List<Feedback> feedbacks = feedbackPackage.getFeedbacks()
                .stream()
                .filter(feedback -> Objects.equals(feedback.getStatus().getName(), FEEDBACK_STATUS_COMPLETED))
                .toList();

        boolean isAllFeedbacksCompleted = feedbacks.size() == feedbackPackage.getFeedbacks().size();

        double averageFeedbackMark = calculateAverageMark(feedbacks);
        double bestAverageFeedbackMark = calculateBestAverageFeedbackMark(feedbacks);
        double worstAverageFeedbackMark = calculateWorstAverageFeedbackMark(feedbacks);

        User bestFeedbackEmployee = findBestFeedbackEmployee(feedbacks);
        User worstFeedbackEmployee = findWorstFeedbackEmployee(feedbacks);

        Skill bestSkill = calculateBestSkill(feedbacks);
        Skill worstSkill = calculateWorstSkill(feedbacks);

        report.setAverageFeedbackMark(averageFeedbackMark);
        report.setBestAverageFeedbackMark(bestAverageFeedbackMark);
        report.setWorstAverageFeedbackMark(worstAverageFeedbackMark);
        report.setBestSkill(bestSkill);
        report.setWorstSkill(worstSkill);
        report.setBestFeedbackEmployee(bestFeedbackEmployee);
        report.setWorstFeedbackEmployee(worstFeedbackEmployee);
        report.setAllFeedbacksCompleted(isAllFeedbacksCompleted);

        return report;
    }

    private double calculateAverageFeedbackMark(Feedback feedback) {
        double averageMark = 0.0;

        for (Response response: feedback.getResponses()) {
            averageMark += response.getRate();
        }

        averageMark /= feedback.getResponses().size();

        return averageMark;
    }

    private List<Double> calculateAverageFeedbacksMarks(List<Feedback> feedbacks) {

        List<Double> averageFeedbacksMarks = new ArrayList<>();
        for (Feedback feedback: feedbacks) {
            averageFeedbacksMarks.add(calculateAverageFeedbackMark(feedback));
        }

        return averageFeedbacksMarks;
    }

    private double calculateAverageMark(List<Feedback> feedbacks) {
        double averageFeedbackMark = 0.0;

        List<Double> averageFeedbacksMarks = calculateAverageFeedbacksMarks(feedbacks);

        for (Double mark: averageFeedbacksMarks) {
            averageFeedbackMark += mark;
        }

        averageFeedbackMark /= averageFeedbacksMarks.size();

        return averageFeedbackMark;
    }

    private double calculateBestAverageFeedbackMark(List<Feedback> feedbacks) {
        return Collections.max(calculateAverageFeedbacksMarks(feedbacks));
    }

    private double calculateWorstAverageFeedbackMark(List<Feedback> feedbacks) {
        return Collections.min(calculateAverageFeedbacksMarks(feedbacks));
    }

    private User findBestFeedbackEmployee(List<Feedback> feedbacks) {
        User employee = new User();

        double bestAverageFeedbackMark = calculateBestAverageFeedbackMark(feedbacks);
        for (Feedback feedback: feedbacks) {
            double averageFeedbackMark = calculateAverageFeedbackMark(feedback);
            if (averageFeedbackMark == bestAverageFeedbackMark) {
                employee = feedback.getSourceUser();
            }
        }

        return employee;
    }

    private User findWorstFeedbackEmployee(List<Feedback> feedbacks) {
        User employee = new User();

        double worstAverageFeedbackMark = calculateWorstAverageFeedbackMark(feedbacks);
        for (Feedback feedback: feedbacks) {
            double averageFeedbackMark = calculateAverageFeedbackMark(feedback);
            if (averageFeedbackMark == worstAverageFeedbackMark) {
                employee = feedback.getSourceUser();
            }
        }

        return employee;
    }

    private Map<Skill, Double> calculateSkillRates(List<Feedback> feedbacks) {
        Map<Skill, Double> skillRates = new HashMap<>();

        for (Feedback feedback: feedbacks) {
            for (Response response: feedback.getResponses()) {
                Skill skill = response.getQuestion().getSkill();

                if (skillRates.containsKey(skill)) {
                    skillRates.put(skill, skillRates.get(skill) + response.getRate());
                } else {
                    skillRates.put(skill, response.getRate());
                }
            }
        }

        return skillRates;
    }

    private Skill calculateBestSkill(List<Feedback> feedbacks) {
        Skill skill = new Skill();
        Map<Skill, Double> skillRates = calculateSkillRates(feedbacks);

        double maxSkillRate = -1.0;

        for (Skill currentSkill: skillRates.keySet()) {
            if (skillRates.get(currentSkill) > maxSkillRate) {
                maxSkillRate = skillRates.get(currentSkill);
                skill = currentSkill;
            }
        }

        return skill;
    }

    private Skill calculateWorstSkill(List<Feedback> feedbacks) {
        Skill skill;

        Map<Skill, Double> skillRates = calculateSkillRates(feedbacks);

        double minSkillRate = skillRates.get(calculateBestSkill(feedbacks));
        skill = calculateBestSkill(feedbacks);

        for (Skill currentSkill: skillRates.keySet()) {
            if (skillRates.get(currentSkill) < minSkillRate) {
                minSkillRate = skillRates.get(currentSkill);
                skill = currentSkill;
            }
        }

        return skill;
    }
}
