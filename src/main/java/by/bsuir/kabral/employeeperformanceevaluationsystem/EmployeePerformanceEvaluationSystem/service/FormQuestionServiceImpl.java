package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.FormQuestion;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.FormQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FormQuestionServiceImpl implements ServiceInterface<FormQuestion> {

    private final FormQuestionRepository formQuestionRepository;

    @Autowired
    public FormQuestionServiceImpl(FormQuestionRepository formQuestionRepository) {
        this.formQuestionRepository = formQuestionRepository;
    }

    @Override
    public List<FormQuestion> findAll() {
        return formQuestionRepository.findAll();
    }

    @Override
    public FormQuestion findById(int id) {
        Optional<FormQuestion> formQuestion = formQuestionRepository.findById(id);

        return formQuestion.orElse(null);
    }

    @Override
    @Transactional
    public void save(FormQuestion formQuestion) {
        formQuestionRepository.save(formQuestion);
    }

    @Override
    @Transactional
    public void update(FormQuestion formQuestion, int id) {
        formQuestion.setId(id);
        formQuestionRepository.save(formQuestion);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        formQuestionRepository.deleteById(id);
    }
}
