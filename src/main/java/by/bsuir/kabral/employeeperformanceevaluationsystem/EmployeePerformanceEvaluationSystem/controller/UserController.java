package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.UserDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Manager;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Position;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Salary;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.ManagerServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.PositionServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.SalaryServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    private final ModelMapper modelMapper;

    private final SalaryServiceImpl salaryService;

    private final PositionServiceImpl positionService;

    private final ManagerServiceImpl managerService;

    @Autowired
    public UserController(UserServiceImpl userService, ModelMapper modelMapper, SalaryServiceImpl salaryService, PositionServiceImpl positionService, ManagerServiceImpl managerService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.salaryService = salaryService;
        this.positionService = positionService;
        this.managerService = managerService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.findAll()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") int id) {
        return convertToUserDTO(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {

        User user = convertToUser(userDTO);
        Salary salary = salaryService.findByValue(userDTO.getSalary().getValue());
        Position position = positionService.findByName(userDTO.getPosition().getName());
        Manager manager = managerService.findByUserId(userDTO.getManager().getUserId());

        user.setSalary(salary);
        user.setPosition(position);
        user.setManager(manager);

        userService.save(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid UserDTO userDTO, @PathVariable("id") int id,
                                             BindingResult bindingResult) {

        User user = convertToUser(userDTO);
        Salary salary = salaryService.findByValue(userDTO.getSalary().getValue());
        Position position = positionService.findByName(userDTO.getPosition().getName());
        Manager manager = managerService.findByUserId(userDTO.getManager().getUserId());

        user.setSalary(salary);
        user.setPosition(position);
        user.setManager(manager);

        userService.update(user, id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

        userService.deleteById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
