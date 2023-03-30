package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.QuestionDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Question;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Skill;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.QuestionServiceImpl;
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
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionServiceImpl questionService;

    private final SkillServiceImpl skillService;

    private final ModelMapper modelMapper;

    @Autowired
    public QuestionController(QuestionServiceImpl questionService, SkillServiceImpl skillService, ModelMapper modelMapper) {
        this.questionService = questionService;
        this.skillService = skillService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<QuestionDTO> getQuestions() {
        return questionService.findAll()
                .stream()
                .map(this::convertToQuestionDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public QuestionDTO getQuestion(@PathVariable("id") int id) {
        return convertToQuestionDTO(questionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid QuestionDTO questionDTO, BindingResult bindingResult) {

        Question question = convertToQuestion(questionDTO);
        Skill skill = skillService.findByName(questionDTO.getSkill().getName());
        question.setSkill(skill);

        questionService.save(question);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid QuestionDTO questionDTO, @PathVariable("id") int id,
                                             BindingResult bindingResult) {

        Question question = convertToQuestion(questionDTO);
        Skill skill = skillService.findByName(questionDTO.getSkill().getName());
        question.setSkill(skill);

        questionService.update(question, id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

        questionService.deleteById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Question convertToQuestion(QuestionDTO questionDTO) {
        return modelMapper.map(questionDTO, Question.class);
    }

    private QuestionDTO convertToQuestionDTO(Question question) {
        return modelMapper.map(question, QuestionDTO.class);
    }
}
