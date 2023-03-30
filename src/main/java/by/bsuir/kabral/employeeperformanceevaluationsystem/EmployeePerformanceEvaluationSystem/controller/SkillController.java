package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.SkillDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Skill;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.SkillServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillServiceImpl skillService;

    private final ModelMapper modelMapper;

    @Autowired
    public SkillController(SkillServiceImpl skillService, ModelMapper modelMapper) {
        this.skillService = skillService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<SkillDTO> getSkills() {
        return skillService.findAll()
                .stream()
                .map(this::convertToSkillDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SkillDTO getSkill(@PathVariable("id") int id) {
        return convertToSkillDTO(skillService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SkillDTO skillDTO, BindingResult bindingResult) {

        skillService.save(convertToSkill(skillDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid SkillDTO skillDTO, @PathVariable("id") int id,
                                             BindingResult bindingResult) {

        skillService.update(convertToSkill(skillDTO), id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

        skillService.deleteById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private SkillDTO convertToSkillDTO(Skill skill) {
        return modelMapper.map(skill, SkillDTO.class);
    }

    private Skill convertToSkill(SkillDTO skillDTO) {
        return modelMapper.map(skillDTO, Skill.class);
    }
}