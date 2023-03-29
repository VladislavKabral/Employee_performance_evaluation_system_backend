package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Salary;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SalaryServiceImpl implements ServiceInterface<Salary> {

    private final SalaryRepository salaryRepository;

    @Autowired
    public SalaryServiceImpl(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public List<Salary> findAll() {
        return salaryRepository.findAll();
    }

    @Override
    public Salary findById(int id) {
        Optional<Salary> salary = salaryRepository.findById(id);

        return salary.orElse(null);
    }

    @Override
    @Transactional
    public void save(Salary salary) {
        salaryRepository.save(salary);
    }

    @Override
    @Transactional
    public void update(Salary salary, int id) {
        salary.setId(id);
        salaryRepository.save(salary);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        salaryRepository.deleteById(id);
    }
}
