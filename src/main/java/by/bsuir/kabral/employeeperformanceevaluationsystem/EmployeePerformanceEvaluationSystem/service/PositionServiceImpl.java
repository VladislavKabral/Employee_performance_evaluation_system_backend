package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Position;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PositionServiceImpl implements ServiceInterface<Position> {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    @Override
    public Position findById(int id) {
        Optional<Position> position = positionRepository.findById(id);

        return position.orElse(null);
    }

    @Override
    @Transactional
    public void save(Position position) {
        positionRepository.save(position);
    }

    @Override
    @Transactional
    public void update(Position position, int id) {
        position.setId(id);
        positionRepository.save(position);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        positionRepository.deleteById(id);
    }
}
