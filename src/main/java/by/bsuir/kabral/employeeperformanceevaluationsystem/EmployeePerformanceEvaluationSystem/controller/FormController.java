package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.FormDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Form;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Question;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.FormServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.QuestionServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.ErrorResponse;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.FormException;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.QuestionException;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.SkillException;
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
@RequestMapping("/forms")
@CrossOrigin(origins = "http://localhost:5173")
public class FormController {

    private final FormServiceImpl formService;

    private final ModelMapper modelMapper;

    private final QuestionServiceImpl questionService;

    @Autowired
    public FormController(FormServiceImpl formService, ModelMapper modelMapper, QuestionServiceImpl questionService) {
        this.formService = formService;
        this.modelMapper = modelMapper;
        this.questionService = questionService;
    }

    @GetMapping
    public List<FormDTO> getForms() {
        return formService.findAll()
                .stream()
                .map(this::convertToFormDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FormDTO getForm(@PathVariable("id") int id) throws FormException {
        return convertToFormDTO(formService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid FormDTO formDTO, BindingResult bindingResult) throws QuestionException, FormException, SkillException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new FormException(message.toString());
        }

        List<Question> questions = formDTO.getQuestions();

        for (Question question: questions) {
            questionService.save(question);
        }

        List<Question> questionsFromDB = new ArrayList<>(questions.size());

        for (Question question: questions) {
            questionsFromDB.add(questionService.findByText(question.getText()));
        }

        Form form = convertToForm(formDTO);
        form.setQuestions(questionsFromDB);

        formService.save(form);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody FormDTO formDTO, @PathVariable("id") int id,
                                             BindingResult bindingResult) throws QuestionException, FormException, SkillException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new FormException(message.toString());
        }

        Form form = formService.findById(id);
        form.setName(formDTO.getName());
        for (Question question: form.getQuestions()) {
            questionService.deleteById(question.getId());
        }
        form.setQuestions(null);

        List<Question> questions = formDTO.getQuestions();

        for (Question question: questions) {
            questionService.save(question);
        }

        List<Question> questionsFromDB = new ArrayList<>(questions.size());

        for (Question question: questions) {
            questionsFromDB.add(questionService.findByText(question.getText()));
        }


        form.setQuestions(questionsFromDB);
        formService.update(form, id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

        formService.deleteById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private Form convertToForm(FormDTO formDTO) {
        return modelMapper.map(formDTO, Form.class);
    }

    private FormDTO convertToFormDTO(Form form) {
        return modelMapper.map(form, FormDTO.class);
    }
}
