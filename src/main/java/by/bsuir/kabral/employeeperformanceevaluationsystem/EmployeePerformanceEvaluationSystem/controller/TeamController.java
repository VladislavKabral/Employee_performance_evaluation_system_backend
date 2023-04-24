package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.TeamDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Team;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.TeamServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.UserServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.ErrorResponse;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.TeamException;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
@CrossOrigin(origins = "http://localhost:5173")
public class TeamController {

    private final TeamServiceImpl teamService;

    private final UserServiceImpl userService;

    private final ModelMapper modelMapper;

    @Autowired
    public TeamController(TeamServiceImpl teamService, UserServiceImpl userService, ModelMapper modelMapper) {
        this.teamService = teamService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<TeamDTO> getTeams() {
        return teamService.findAll()
                .stream()
                .map(this::convertToTeamDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TeamDTO  getTeam(@PathVariable("id") int id) throws TeamException {
        return convertToTeamDTO(teamService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid TeamDTO teamDTO, BindingResult bindingResult) throws TeamException, UserException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new TeamException(message.toString());
        }

        List<User> users = new ArrayList<>(teamDTO.getUsers().size());
        Team team = convertToTeam(teamDTO);
        for (User user: teamDTO.getUsers()) {
            User currentUser = userService.findById(user.getId());
            currentUser.setTeam(team);
            users.add(currentUser);
        }
        team.setUsers(users);
        teamService.save(team);

        for (User user: users) {
            userService.update(user, user.getId());
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid TeamDTO teamDTO, @PathVariable("id") int id,
                                             BindingResult bindingResult) throws TeamException, UserException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new TeamException(message.toString());
        }

        List<User> users = new ArrayList<>(teamDTO.getUsers().size());
        Team team = teamService.findById(id);

        for (User user: team.getUsers()) {
            user.setTeam(null);
            userService.update(user, user.getId());
        }

        for (User user: teamDTO.getUsers()) {
            User currentUser = userService.findById(user.getId());
            currentUser.setTeam(team);
            users.add(currentUser);
        }
        team.setName(teamDTO.getName());
        team.setUsers(users);

        for (User user: users) {
            userService.update(user, user.getId());
        }

        teamService.update(team, id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) throws TeamException {

        Team team = teamService.findById(id);

        for (User user: team.getUsers()) {
            user.setTeam(null);
            userService.update(user, user.getId());
        }

        teamService.deleteById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleResponse(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private Team convertToTeam(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    private TeamDTO convertToTeamDTO(Team team) {
        return modelMapper.map(team, TeamDTO.class);
    }
}
