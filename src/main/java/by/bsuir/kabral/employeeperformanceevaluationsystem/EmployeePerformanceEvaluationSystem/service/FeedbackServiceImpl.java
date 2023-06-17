package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Feedback;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.FeedbackRepository;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.FeedbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    private static final int COUNT_OF_FEEDBACKS_ON_PAGE = 10;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackStatusServiceImpl feedbackStatusService) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackStatusService = feedbackStatusService;
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> findFeedbacksByPage(int indexOfPage) {
        return feedbackRepository.findAll(PageRequest.of(indexOfPage, COUNT_OF_FEEDBACKS_ON_PAGE)).getContent();
    }

    @Override
    public Feedback findById(int id) throws FeedbackException {
        Optional<Feedback> feedback = feedbackRepository.findById(id);

        if (feedback.isEmpty()) {
            throw new FeedbackException("Feedback not found");
        }

        return feedback.get();
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
