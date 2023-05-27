package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Position;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.UserStatus;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.UserRepository;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.UserException;
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

    private final PositionServiceImpl positionService;

    private static final String USER_STATUS_ACTIVE = "ACTIVE";

    private static final String USER_DEFAULT_POSITION = "Worker";

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserStatusServiceImpl userStatusService, PositionServiceImpl positionService) {
        this.userRepository = userRepository;
        this.userStatusService = userStatusService;
        this.positionService = positionService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) throws UserException {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        return user.get();
    }

    public User findByLastnameAndFirstname(String lastname, String firstname) throws UserException {
        Optional<User> user = Optional.ofNullable(userRepository.findByLastnameAndFirstname(lastname, firstname));

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        return user.get();
    }

    public User findByLastname(String lastname) throws UserException {
        Optional<User> user = Optional.ofNullable(userRepository.findByLastname(lastname));

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        return user.get();
    }

    public User findByEmail(String email) throws UserException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        return user.get();
    }

    @Override
    @Transactional
    public void save(User user) {
        UserStatus userStatus = userStatusService.findByName(USER_STATUS_ACTIVE);
        Position position = positionService.findByName(USER_DEFAULT_POSITION);
        user.setPosition(position);
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
