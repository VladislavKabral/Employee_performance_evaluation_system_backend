package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Question;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class QuestionServiceImpl implements ServiceInterface<Question> {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(int id) {
        Optional<Question> question = questionRepository.findById(id);

        return question.orElse(null);
    }

    public Question findByText(String text) {
        Optional<Question> question = Optional.ofNullable(questionRepository.findByText(text));

        return question.orElse(null);
    }

    @Override
    @Transactional
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    @Transactional
    public void update(Question question, int id) {
        question.setId(id);
        questionRepository.save(question);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }
}
