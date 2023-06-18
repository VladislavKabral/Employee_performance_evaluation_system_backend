package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.FeedbackPackage;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.FeedbackPackageRepository;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.FeedbackPackageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FeedbackPackageServiceImpl implements ServiceInterface<FeedbackPackage> {

    private final FeedbackPackageRepository feedbackPackageRepository;

    private static final int COUNT_OF_FEEDBACK_PACKAGES_ON_PAGE = 10;

    @Autowired
    public FeedbackPackageServiceImpl(FeedbackPackageRepository feedbackPackageRepository) {
        this.feedbackPackageRepository = feedbackPackageRepository;
    }

    public List<FeedbackPackage> findAll() {
        return feedbackPackageRepository.findAll();
    }

    public List<FeedbackPackage> findFeedbackPackagesByPage(int indexOfPage) {
        return feedbackPackageRepository.findAll(PageRequest.of(indexOfPage, COUNT_OF_FEEDBACK_PACKAGES_ON_PAGE))
                .getContent();
    }

    public FeedbackPackage findById(int id) throws FeedbackPackageException {
        Optional<FeedbackPackage> feedbackPackage = feedbackPackageRepository.findById(id);

        if (feedbackPackage.isEmpty()) {
            throw new FeedbackPackageException("Feedback package not found");
        }

        return feedbackPackage.get();
    }

    @Transactional
    public void save(FeedbackPackage feedbackPackage) {
        feedbackPackage.setCreationDate(LocalDate.now());
        feedbackPackageRepository.save(feedbackPackage);
    }

    @Transactional
    public void update(FeedbackPackage feedbackPackage, int id) {
        feedbackPackage.setId(id);
        feedbackPackage.setCreationDate(LocalDate.now());
        feedbackPackageRepository.save(feedbackPackage);
    }

    @Transactional
    public void deleteById(int id) {
        feedbackPackageRepository.deleteById(id);
    }
}
