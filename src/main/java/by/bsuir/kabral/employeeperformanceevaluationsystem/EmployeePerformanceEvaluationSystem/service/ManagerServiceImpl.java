package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Manager;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ManagerServiceImpl implements ServiceInterface<Manager> {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager findById(int id) {
        Optional<Manager> manager = managerRepository.findById(id);

        return manager.orElse(null);
    }

    @Override
    @Transactional
    public void save(Manager manager) {
        managerRepository.save(manager);
    }

    @Override
    @Transactional
    public void update(Manager manager, int id) {
        manager.setId(id);
        managerRepository.save(manager);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        managerRepository.deleteById(id);
    }
}
