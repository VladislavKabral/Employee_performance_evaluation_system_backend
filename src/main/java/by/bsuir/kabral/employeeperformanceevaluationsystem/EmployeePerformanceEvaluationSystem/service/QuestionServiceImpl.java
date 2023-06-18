package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Question;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.QuestionRepository;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.QuestionException;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.SkillException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class QuestionServiceImpl implements ServiceInterface<Question> {

    private final QuestionRepository questionRepository;

    private final SkillServiceImpl skillService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, SkillServiceImpl skillService) {
        this.questionRepository = questionRepository;
        this.skillService = skillService;
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(int id) throws QuestionException {
        Optional<Question> question = questionRepository.findById(id);

        if (question.isEmpty()) {
            throw new QuestionException("Question not found");
        }

        return question.get();
    }

    public Question findByText(String text) throws QuestionException {
        Optional<Question> question = Optional.ofNullable(questionRepository.findByText(text));

        if (question.isEmpty()) {
            throw new QuestionException("Question not found");
        }

        return question.get();
    }

    @Override
    @Transactional
    public void save(Question question) throws SkillException {
        Question questionForDB = new Question();
        questionForDB.setText(question.getText());
        questionForDB.setSkill(skillService.findByName(question.getSkill().getName()));
        questionRepository.save(questionForDB);
    }

    @Override
    @Transactional
    public void update(Question question, int id) throws SkillException {
        Question questionForDB = new Question();
        questionForDB.setText(question.getText());
        questionForDB.setSkill(skillService.findByName(question.getSkill().getName()));
        questionForDB.setId(id);
        questionRepository.save(questionForDB);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }
}
