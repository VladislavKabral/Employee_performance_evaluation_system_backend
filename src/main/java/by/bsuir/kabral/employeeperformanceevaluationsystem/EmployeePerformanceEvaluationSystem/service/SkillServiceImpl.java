package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Skill;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.SkillRepository;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.SkillException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SkillServiceImpl implements ServiceInterface<Skill> {

    private final SkillRepository skillRepository;

    private static final int COUNT_OF_SKILLS_ON_PAGE = 10;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public List<Skill> findSkillsByPage(int indexOfPage) {
        return skillRepository.findAll(PageRequest.of(indexOfPage, COUNT_OF_SKILLS_ON_PAGE)).getContent();
    }

    @Override
    public Skill findById(int id) throws SkillException {
        Optional<Skill> skill = skillRepository.findById(id);

        if (skill.isEmpty()) {
            throw new SkillException("Skill not found");
        }

        return skill.get();
    }

    public Skill findByName(String name) throws SkillException {
        Optional<Skill> skill = Optional.ofNullable(skillRepository.findByName(name));

        if (skill.isEmpty()) {
            throw new SkillException("Skill not found");
        }

        return skill.get();
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
