package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Form;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Question;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.FormRepository;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.FormException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FormServiceImpl implements ServiceInterface<Form> {

    private final FormRepository formRepository;

    private final QuestionServiceImpl questionService;

    @Autowired
    public FormServiceImpl(FormRepository formRepository, QuestionServiceImpl questionService) {
        this.formRepository = formRepository;
        this.questionService = questionService;
    }

    @Override
    public List<Form> findAll() {
        return formRepository.findAll();
    }

    @Override
    public Form findById(int id) throws FormException {
        Optional<Form> form = formRepository.findById(id);

        if (form.isEmpty()) {
            throw new FormException("Form not found");
        }

        return form.get();
    }

    public Form findByName(String name) throws FormException {
        Optional<Form> form = Optional.ofNullable(formRepository.findByName(name));

        if (form.isEmpty()) {
            throw new FormException("Form not found");
        }

        return form.get();
    }

    @Override
    @Transactional
    public void save(Form form) {
        formRepository.save(form);
    }

    @Override
    @Transactional
    public void update(Form form, int id) {
        form.setId(id);
        formRepository.save(form);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Optional<Form> form = formRepository.findById(id);

        if (form.isPresent()) {
            for (Question question: form.get().getQuestions()) {
                questionService.deleteById(question.getId());
            }
        }

        formRepository.deleteById(id);
    }
}
