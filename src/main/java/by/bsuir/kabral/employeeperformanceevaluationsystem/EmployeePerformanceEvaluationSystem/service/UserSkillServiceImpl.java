package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.UserSkill;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserSkillServiceImpl implements ServiceInterface<UserSkill> {

    private final UserSkillRepository userSkillRepository;

    @Autowired
    public UserSkillServiceImpl(UserSkillRepository userSkillRepository) {
        this.userSkillRepository = userSkillRepository;
    }

    @Override
    public List<UserSkill> findAll() {
        return userSkillRepository.findAll();
    }

    @Override
    public UserSkill findById(int id) {
        Optional<UserSkill> userSkill = userSkillRepository.findById(id);

        return userSkill.orElse(null);
    }

    @Override
    @Transactional
    public void save(UserSkill userSkill) {
        userSkillRepository.save(userSkill);
    }

    @Override
    @Transactional
    public void update(UserSkill userSkill, int id) {
        userSkill.setId(id);
        userSkillRepository.save(userSkill);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userSkillRepository.deleteById(id);
    }
}
