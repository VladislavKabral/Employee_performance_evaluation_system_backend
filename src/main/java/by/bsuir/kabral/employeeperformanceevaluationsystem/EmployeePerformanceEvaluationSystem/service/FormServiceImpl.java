package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Form;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FormServiceImpl implements ServiceInterface<Form> {

    private final FormRepository formRepository;

    @Autowired
    public FormServiceImpl(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Override
    public List<Form> findAll() {
        return formRepository.findAll();
    }

    @Override
    public Form findById(int id) {
        Optional<Form> form = formRepository.findById(id);

        return form.orElse(null);
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
        formRepository.deleteById(id);
    }
}
