package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.UserStatus;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserStatusServiceImpl implements ServiceInterface<UserStatus> {

    private final UserStatusRepository userStatusRepository;

    @Autowired
    public UserStatusServiceImpl(UserStatusRepository userStatusRepository) {
        this.userStatusRepository = userStatusRepository;
    }

    @Override
    public List<UserStatus> findAll() {
        return userStatusRepository.findAll();
    }

    @Override
    public UserStatus findById(int id) {
        Optional<UserStatus> userStatus = userStatusRepository.findById(id);

        return userStatus.orElse(null);
    }

    @Override
    @Transactional
    public void save(UserStatus userStatus) {
        userStatusRepository.save(userStatus);
    }

    @Override
    @Transactional
    public void update(UserStatus userStatus, int id) {
        userStatus.setId(id);
        userStatusRepository.save(userStatus);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userStatusRepository.deleteById(id);
    }
}
