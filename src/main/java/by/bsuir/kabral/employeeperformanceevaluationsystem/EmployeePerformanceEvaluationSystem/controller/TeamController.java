package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.TeamDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.TeamStatisticDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Team;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.statistic.TeamStatistic;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.TeamServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.TeamStatisticService;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.UserServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.ErrorResponse;
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
@RequestMapping("/teams")
@CrossOrigin(origins = "http://localhost:5173")
public class TeamController {

    private final TeamServiceImpl teamService;

    private final UserServiceImpl userService;

    private final ModelMapper modelMapper;

    private final TeamStatisticService teamStatisticService;

    @Autowired
    public TeamController(TeamServiceImpl teamService, UserServiceImpl userService, ModelMapper modelMapper, TeamStatisticService teamStatisticService) {
        this.teamService = teamService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.teamStatisticService = teamStatisticService;
    }

    @GetMapping
    public List<TeamDTO> getTeams() {
        return teamService.findAll()
                .stream()
                .map(this::convertToTeamDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TeamDTO getTeam(@PathVariable("id") int id) throws TeamException {
        return convertToTeamDTO(teamService.findById(id));
    }

    @GetMapping("/{id}/statistic")
    public TeamStatisticDTO getTeamStatistic(@PathVariable("id") int id) throws TeamException {
        Team team = teamService.findById(id);

        TeamStatistic teamStatistic = teamStatisticService.generateTeamStatistic(team);

        TeamStatisticDTO teamStatisticDTO = new TeamStatisticDTO();
        teamStatisticDTO.setAverageFeedbackMark(teamStatistic.getAverageFeedbackMark());
        teamStatisticDTO.setBestAverageFeedbackMark(teamStatistic.getBestAverageFeedbackMark());
        teamStatisticDTO.setWorstAverageFeedbackMark(teamStatistic.getWorstAverageFeedbackMark());
        teamStatisticDTO.setBestSkill(teamStatistic.getBestSkill());
        teamStatisticDTO.setWorstSkill(teamStatistic.getWorstSkill());
        teamStatisticDTO.setBestEmployee(teamStatistic.getBestEmployee());
        teamStatisticDTO.setWorstEmployee(teamStatistic.getWorstEmployee());

        List<Integer> marks = new ArrayList<>();
        Map<Double, Integer> distributionOfMarks = teamStatistic.getDistributionOfMarks();

        for (int i = 1; i <= 10; i++) {
            marks.add(distributionOfMarks.get((double) i));
        }

        teamStatisticDTO.setDistributionOfMarks(marks);

        return teamStatisticDTO;
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
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

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
