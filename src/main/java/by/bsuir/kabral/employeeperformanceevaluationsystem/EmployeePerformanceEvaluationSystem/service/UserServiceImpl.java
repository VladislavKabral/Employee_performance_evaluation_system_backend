package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.UserStatus;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements ServiceInterface<User> {

    private final UserRepository userRepository;

    private final UserStatusServiceImpl userStatusService;

    private static final String USER_STATUS_ACTIVE = "ACTIVE";

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserStatusServiceImpl userStatusService) {
        this.userRepository = userRepository;
        this.userStatusService = userStatusService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElse(null);
    }

    public User findByLastnameAndFirstname(String lastname, String firstname) {
        Optional<User> user = Optional.ofNullable(userRepository.findByLastnameAndFirstname(lastname, firstname));

        return user.orElse(null);
    }

    public User findByLastname(String lastname) {
        Optional<User> user = Optional.ofNullable(userRepository.findByLastname(lastname));

        return user.orElse(null);
    }

    @Override
    @Transactional
    public void save(User user) {
        UserStatus userStatus = userStatusService.findByName(USER_STATUS_ACTIVE);
        user.setStatus(userStatus);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user, int id) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
