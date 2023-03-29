package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Feedback;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements ServiceInterface<Feedback> {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
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
        feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public void update(Feedback feedback, int id) {
        feedback.setId(id);
        feedbackRepository.save(feedback);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        feedbackRepository.deleteById(id);
    }
}
