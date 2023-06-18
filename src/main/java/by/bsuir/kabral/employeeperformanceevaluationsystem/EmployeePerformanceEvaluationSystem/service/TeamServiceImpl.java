package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Team;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.TeamRepository;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.TeamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeamServiceImpl implements ServiceInterface<Team> {

    private final TeamRepository teamRepository;

    private final UserServiceImpl userService;

    private static final int COUNT_OF_TEAMS_ON_PAGE = 10;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, UserServiceImpl userService) {
        this.teamRepository = teamRepository;
        this.userService = userService;
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public List<Team> findTeamsByPage(int indexOfPage) {
        return teamRepository.findAll(PageRequest.of(indexOfPage, COUNT_OF_TEAMS_ON_PAGE)).getContent();
    }

    @Override
    public Team findById(int id) throws TeamException {
        Optional<Team> team = teamRepository.findById(id);

        if (team.isEmpty()) {
            throw new TeamException("Team not found");
        }

        return team.get();
    }

    public Team findByName(String name) throws TeamException {
        Optional<Team> team = Optional.ofNullable(teamRepository.findByName(name));

        if (team.isEmpty()) {
            throw new TeamException("Team not found");
        }

        return team.get();
    }

    @Override
    @Transactional
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Override
    @Transactional
    public void update(Team team, int id) {
        team.setId(id);
        teamRepository.save(team);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Optional<Team> team = teamRepository.findById(id);

        if (team.isPresent()) {
            for (User user: team.get().getUsers()) {
                user.setTeam(null);
                userService.update(user, user.getId());
            }
            team.get().setUsers(null);
            save(team.get());
        }


        teamRepository.deleteById(id);
    }
}
