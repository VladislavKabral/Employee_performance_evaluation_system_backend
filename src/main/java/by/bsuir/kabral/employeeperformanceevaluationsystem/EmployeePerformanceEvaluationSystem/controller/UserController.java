package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.ManagerDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.UserDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.UserStatisticDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.*;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.statistic.UserStatistic;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.*;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.ErrorResponse;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.ManagerException;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.TeamException;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.UserException;
import javax.validation.Valid;
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

    private final TeamServiceImpl teamService;

    @Autowired
    public UserController(UserServiceImpl userService, ModelMapper modelMapper, SalaryServiceImpl salaryService, PositionServiceImpl positionService, ManagerServiceImpl managerService, UserStatisticService userStatisticService, TeamServiceImpl teamService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.salaryService = salaryService;
        this.positionService = positionService;
        this.managerService = managerService;
        this.userStatisticService = userStatisticService;
        this.teamService = teamService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {

        return userService.getActiveUsers()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("page/{indexOfPage}")
    public List<UserDTO> getUsersByPage(@PathVariable("indexOfPage") int indexOfPage) {
        return userService.findUserByPage(indexOfPage)
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") int id) throws UserException {
        return convertToUserDTO(userService.findById(id));
    }

    @PostMapping("/search")
    public List<UserDTO> searchUsers(@RequestBody UserDTO userDTO) {
        return userService.findByLastnameStartingWith(userDTO.getLastname())
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/manager/{userId}/users")
    public List<UserDTO> getManagerUsers(@PathVariable("userId") int userId) throws ManagerException {
        Manager manager = managerService.findByUserId(userId);

        return manager.getUsers()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/managers")
    public List<ManagerDTO> getManagers() throws UserException {
        List<Manager> managers = managerService.findAll();

        List<ManagerDTO> managerDTOS = new ArrayList<>(managers.size());

        for (Manager manager: managers) {
            ManagerDTO managerDTO = new ManagerDTO();
            User user = userService.findById(manager.getUserId());
            managerDTO.setId(manager.getId());
            managerDTO.setLastname(user.getLastname());
            managerDTO.setFirstname(user.getFirstname());
            managerDTOS.add(managerDTO);
        }

        return managerDTOS;
    }

    @GetMapping("/managers/{managerId}")
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

        for (int i = 1; i <= 10; i++) {
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

    @PatchMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> edit(@PathVariable("id") int id, @RequestBody UserDTO userDTO)
            throws UserException, TeamException, ManagerException {

        User user = userService.findById(id);

        if (userDTO.getSalary() != null) {
            Salary salary = salaryService.findByValue(userDTO.getSalary().getValue());
            user.setSalary(salary);
        }

        if (userDTO.getTeam() != null) {
            Team team = teamService.findByName(userDTO.getTeam().getName());
            user.setTeam(team);
        }

        if (userDTO.getManager() != null) {
            Manager manager = managerService.findById(userDTO.getManager().getId());
            user.setManager(manager);
        }

        userService.update(user, id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/retire")
    public ResponseEntity<HttpStatus> retire(@PathVariable("id") int id) throws UserException {
        User user = userService.findById(id);

        userService.retire(user, id);

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
