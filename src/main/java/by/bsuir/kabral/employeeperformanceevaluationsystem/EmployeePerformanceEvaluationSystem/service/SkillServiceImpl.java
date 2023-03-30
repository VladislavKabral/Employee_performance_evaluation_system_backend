package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Skill;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SkillServiceImpl implements ServiceInterface<Skill> {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill findById(int id) {
        Optional<Skill> skill = skillRepository.findById(id);

        return skill.orElse(null);
    }

    public Skill findByName(String name) {
        Optional<Skill> skill = Optional.ofNullable(skillRepository.findByName(name));

        return skill.orElse(null);
    }

    @Override
    @Transactional
    public void save(Skill skill) {
        skillRepository.save(skill);
    }

    @Override
    @Transactional
    public void update(Skill skill, int id) {
        skill.setId(id);
        skillRepository.save(skill);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        skillRepository.deleteById(id);
    }
}
