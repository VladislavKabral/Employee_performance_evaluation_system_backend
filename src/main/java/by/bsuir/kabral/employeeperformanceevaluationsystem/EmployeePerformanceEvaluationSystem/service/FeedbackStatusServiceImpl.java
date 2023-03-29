package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.FeedbackStatus;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.FeedbackStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FeedbackStatusServiceImpl implements ServiceInterface<FeedbackStatus> {

    private final FeedbackStatusRepository feedbackStatusRepository;
    @Autowired
    public FeedbackStatusServiceImpl(FeedbackStatusRepository feedbackStatusRepository) {
        this.feedbackStatusRepository = feedbackStatusRepository;
    }

    @Override
    public List<FeedbackStatus> findAll() {
        return feedbackStatusRepository.findAll();
    }

    @Override
    public FeedbackStatus findById(int id) {
        Optional<FeedbackStatus> feedbackStatus = feedbackStatusRepository.findById(id);

        return feedbackStatus.orElse(null);
    }

    @Override
    @Transactional
    public void save(FeedbackStatus feedbackStatus) {
        feedbackStatusRepository.save(feedbackStatus);
    }

    @Override
    @Transactional
    public void update(FeedbackStatus feedbackStatus, int id) {
        feedbackStatus.setId(id);
        feedbackStatusRepository.save(feedbackStatus);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        feedbackStatusRepository.deleteById(id);
    }
}
