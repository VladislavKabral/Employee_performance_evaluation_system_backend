package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.*;

import java.util.List;

public interface ServiceInterface<T> {
    List<T> findAll();

    T findById(int id) throws FeedbackException, FeedbackPackageException, FormException, ManagerException, QuestionException, SkillException, TeamException, UserException;

    void save(T t) throws SkillException;

    void update(T t, int id) throws SkillException;

    void deleteById(int id);
}
