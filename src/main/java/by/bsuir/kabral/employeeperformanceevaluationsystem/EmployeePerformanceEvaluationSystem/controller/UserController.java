package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.UserDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.UserStatisticDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Manager;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Position;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Salary;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.statistic.UserStatistic;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.*;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.ErrorResponse;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.ManagerException;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.UserException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserServiceImpl userService;

    private final ModelMapper modelMapper;

    private final SalaryServiceImpl salaryService;

    private final PositionServiceImpl positionService;

    private final ManagerServiceImpl managerService;

    private final UserStatisticService userStatisticService;

    @Autowired
    public UserController(UserServiceImpl userService, ModelMapper modelMapper, SalaryServiceImpl salaryService, PositionServiceImpl positionService, ManagerServiceImpl managerService, UserStatisticService userStatisticService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.salaryService = salaryService;
        this.positionService = positionService;
        this.managerService = managerService;
        this.userStatisticService = userStatisticService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.findAll()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") int id) throws UserException {
        return convertToUserDTO(userService.findById(id));
    }

    @GetMapping("/manager/{managerId}/users")
    public List<UserDTO> getManagerUsers(@PathVariable("managerId") int managerId) throws ManagerException {
        Manager manager = managerService.findById(managerId);

        return manager.getUsers()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/manager/{managerId}")
    public UserDTO getManager(@PathVariable("managerId") int managerId) throws UserException {
        User manager = userService.findById(managerId);

        return convertToUserDTO(manager);
    }

    @GetMapping("/{id}/statistic")
    public UserStatisticDTO getUserStatistic(@PathVariable("id") int id) throws UserException {
        User user = userService.findById(id);

        UserStatistic userStatistic = userStatisticService.generateUserStatistic(user);

        UserStatisticDTO userStatisticDTO = new UserStatisticDTO();
        userStatisticDTO.setAverageFeedbackMark(userStatistic.getAverageFeedbackMark());
        userStatisticDTO.setBestAverageFeedbackMark(userStatistic.getBestAverageFeedbackMark());
        userStatisticDTO.setWorstAverageFeedbackMark(userStatistic.getWorstAverageFeedbackMark());
        userStatisticDTO.setBestSkill(userStatistic.getBestSkill());
        userStatisticDTO.setWorstSkill(userStatistic.getWorstSkill());
        userStatisticDTO.setBestFeedbackEmployee(userStatistic.getBestFeedbackEmployee());
        userStatisticDTO.setWorstFeedbackEmployee(userStatistic.getWorstFeedbackEmployee());

        List<Integer> marks = new ArrayList<>();
        Map<Double, Integer> distributionOfMarks = userStatistic.getDistributionOfMarks();

        for (int i = 0; i <= 10; i++) {
            marks.add(distributionOfMarks.get((double) i));
        }

        userStatisticDTO.setDistributionOfMarks(marks);

        return userStatisticDTO;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws ManagerException, UserException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new UserException(message.toString());
        }

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
                                             BindingResult bindingResult) throws ManagerException, UserException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new UserException(message.toString());
        }

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

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleResponse(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
