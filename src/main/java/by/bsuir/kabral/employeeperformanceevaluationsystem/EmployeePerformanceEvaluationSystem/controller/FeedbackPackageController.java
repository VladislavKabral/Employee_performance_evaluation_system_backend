package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.FeedbackPackageDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.RequestToFeedbackDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Feedback;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.FeedbackPackage;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Form;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.*;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/packages")
@CrossOrigin(origins = "http://localhost:5173")
public class FeedbackPackageController {

    private final FeedbackPackageServiceImpl feedbackPackageService;

    private final ModelMapper modelMapper;

    private final UserServiceImpl userService;

    private final FormServiceImpl formService;

    private final FeedbackStatusServiceImpl feedbackStatusService;

    private final FeedbackServiceImpl feedbackService;

    private static final String FEEDBACK_STATUS_REQUIRED = "REQUIRED";

    @Autowired
    public FeedbackPackageController(FeedbackPackageServiceImpl feedbackPackageService, ModelMapper modelMapper, UserServiceImpl userService, FormServiceImpl formService, FeedbackStatusServiceImpl feedbackStatusService, FeedbackServiceImpl feedbackService) {
        this.feedbackPackageService = feedbackPackageService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.formService = formService;
        this.feedbackStatusService = feedbackStatusService;
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public List<FeedbackPackageDTO> getPackages() {
        return feedbackPackageService.findAll()
                .stream()
                .map(this::convertToFeedbackPackageDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FeedbackPackageDTO getPackage(@PathVariable("id") int id) throws FeedbackPackageException {
        return convertToFeedbackPackageDTO(feedbackPackageService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public List<FeedbackPackageDTO> getUserPackages(@PathVariable("userId") int userId) throws UserException {
        User user = userService.findById(userId);

        return user.getPackages()
                .stream()
                .map(this::convertToFeedbackPackageDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid FeedbackPackageDTO feedbackPackageDTO,
                                             BindingResult bindingResult) throws FormException, UserException, FeedbackPackageException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new FeedbackPackageException(message.toString());
        }

        FeedbackPackage feedbackPackage = convertToFeedbackPackage(feedbackPackageDTO);
        Form form = formService.findByName(feedbackPackageDTO.getForm().getName());
        User user = userService.findByLastnameAndFirstname(feedbackPackageDTO.getTargetUser().getLastname(),
                feedbackPackageDTO.getTargetUser().getFirstname());

        feedbackPackage.setForm(form);
        feedbackPackage.setTargetUser(user);

        feedbackPackageService.save(feedbackPackage);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/request")
    public ResponseEntity<HttpStatus> sendRequestToFeedback(@RequestBody @Valid RequestToFeedbackDTO requestToFeedbackDTO,
                                                            @PathVariable("id") int id) throws FeedbackPackageException, UserException {

        FeedbackPackage feedbackPackage = feedbackPackageService.findById(id);
        List<User> users = new ArrayList<>(requestToFeedbackDTO.getUsers().size());

        for (User user: requestToFeedbackDTO.getUsers()) {
            users.add(userService.findById(user.getId()));
        }

        List<Feedback> feedbacks = new ArrayList<>(users.size());

        for (User user: users) {
            Feedback feedback = new Feedback();
            feedback.setSourceUser(user);
            feedback.setDate(LocalDate.now());
            feedback.setFeedbackPackage(feedbackPackage);
            feedback.setStatus(feedbackStatusService.findByName(FEEDBACK_STATUS_REQUIRED));
            feedbacks.add(feedback);
            feedbackService.save(feedback);
        }

        feedbackPackage.setFeedbacks(feedbacks);
        feedbackPackageService.update(feedbackPackage, id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid FeedbackPackageDTO feedbackPackageDTO,
                                             @PathVariable("id") int id, BindingResult bindingResult) throws FormException, UserException, FeedbackPackageException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new FeedbackPackageException(message.toString());
        }

        FeedbackPackage feedbackPackage = convertToFeedbackPackage(feedbackPackageDTO);
        Form form = formService.findByName(feedbackPackageDTO.getForm().getName());
        User user = userService.findByLastnameAndFirstname(feedbackPackageDTO.getTargetUser().getLastname(),
                feedbackPackageDTO.getTargetUser().getFirstname());

        feedbackPackage.setForm(form);
        feedbackPackage.setTargetUser(user);

        feedbackPackageService.update(feedbackPackage, id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

        feedbackPackageService.deleteById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private FeedbackPackage convertToFeedbackPackage(FeedbackPackageDTO feedbackPackageDTO) {
        return modelMapper.map(feedbackPackageDTO, FeedbackPackage.class);
    }

    private FeedbackPackageDTO convertToFeedbackPackageDTO(FeedbackPackage feedbackPackage) {
        return modelMapper.map(feedbackPackage, FeedbackPackageDTO.class);
    }
}
