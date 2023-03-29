package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Team;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeamServiceImpl implements ServiceInterface<Team> {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team findById(int id) {
        Optional<Team> team = teamRepository.findById(id);

        return team.orElse(null);
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
        teamRepository.deleteById(id);
    }
}
