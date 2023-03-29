package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import java.util.List;

public interface ServiceInterface<T> {
    List<T> findAll();

    T findById(int id);

    void save(T t);

    void update(T t, int id);

    void deleteById(int id);
}
