package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Feedback;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements ServiceInterface<Feedback> {

    private final FeedbackRepository feedbackRepository;

    private final FeedbackStatusServiceImpl feedbackStatusService;

    private static final String FEEDBACK_STATUS_COMPLETED = "COMPLETED";
    private static final String FEEDBACK_STATUS_REQUIRED = "REQUIRED";

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackStatusServiceImpl feedbackStatusService) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackStatusService = feedbackStatusService;
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback findById(int id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);

        return feedback.orElse(null);
    }

    @Override
    @Transactional
    public void save(Feedback feedback) {
        feedback.setDate(LocalDate.now());
        feedback.setStatus(feedbackStatusService.findByName(FEEDBACK_STATUS_REQUIRED));
        feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public void update(Feedback feedback, int id) {
        feedback.setId(id);
        feedback.setStatus(feedbackStatusService.findByName(FEEDBACK_STATUS_COMPLETED));
        feedback.setDate(LocalDate.now());
        feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        feedbackRepository.deleteById(id);
    }
}
