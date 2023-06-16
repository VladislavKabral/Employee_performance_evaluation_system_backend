package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.*;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.UserRepository;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements ServiceInterface<User> {

    private final UserRepository userRepository;

    private final UserStatusServiceImpl userStatusService;

    private final PositionServiceImpl positionService;

    private final UserRoleService userRoleService;

    private final SalaryServiceImpl salaryService;

    private final PasswordEncoder passwordEncoder;

    private static final String USER_STATUS_ACTIVE = "ACTIVE";

    private static final String USER_STATUS_RETIRED = "RETIRED";

    private static final String USER_DEFAULT_POSITION = "Worker";

    private static final String USER_ROLE_WORKER = "WORKER";

    private static final int EMPLOYEE_MIN_SALARY_VALUE = 400;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserStatusServiceImpl userStatusService, PositionServiceImpl positionService, UserRoleService userRoleService, SalaryServiceImpl salaryService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userStatusService = userStatusService;
        this.positionService = positionService;
        this.userRoleService = userRoleService;
        this.salaryService = salaryService;
        this.passwordEncoder = passwordEncoder;
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
        UserRole userRole = userRoleService.findByName(USER_ROLE_WORKER);
        Salary salary = salaryService.findByValue(String.valueOf(EMPLOYEE_MIN_SALARY_VALUE));
        user.setRole(userRole);
        user.setPosition(position);
        user.setSalary(salary);
        user.setStatus(userStatus);
        user.setHashPassword(passwordEncoder.encode(user.getHashPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user, int id) {
        user.setId(id);
        userRepository.save(user);
    }

    @Transactional
    public void retire(User user, int id) {
        UserStatus userStatus = userStatusService.findByName(USER_STATUS_RETIRED);
        user.setStatus(userStatus);
        user.setTeam(null);
        user.setManager(null);
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getActiveUsers() {
        return findAll()
                .stream()
                .filter(user -> Objects.equals(user.getStatus().getName(), USER_STATUS_ACTIVE))
                .toList();
    }
}
