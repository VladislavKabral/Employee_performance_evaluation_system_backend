package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.FeedbackPackage;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.FeedbackPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FeedbackPackageServiceImpl implements ServiceInterface<FeedbackPackage> {

    private final FeedbackPackageRepository feedbackPackageRepository;

    @Autowired
    public FeedbackPackageServiceImpl(FeedbackPackageRepository feedbackPackageRepository) {
        this.feedbackPackageRepository = feedbackPackageRepository;
    }

    public List<FeedbackPackage> findAll() {
        return feedbackPackageRepository.findAll();
    }

    public FeedbackPackage findById(int id) {
        Optional<FeedbackPackage> feedbackPackage = feedbackPackageRepository.findById(id);

        return feedbackPackage.orElse(null);
    }

    @Transactional
    public void save(FeedbackPackage feedbackPackage) {
        feedbackPackageRepository.save(feedbackPackage);
    }

    @Transactional
    public void update(FeedbackPackage feedbackPackage, int id) {
        feedbackPackage.setId(id);
        feedbackPackageRepository.save(feedbackPackage);
    }

    @Transactional
    public void deleteById(int id) {
        feedbackPackageRepository.deleteById(id);
    }
}
