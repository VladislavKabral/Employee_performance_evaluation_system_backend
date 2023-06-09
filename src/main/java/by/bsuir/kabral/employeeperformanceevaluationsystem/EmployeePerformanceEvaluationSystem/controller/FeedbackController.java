package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.FeedbackDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Feedback;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.FeedbackPackage;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Response;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.*;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.*;
import javax.validation.Valid;
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
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feedbacks")
@CrossOrigin(origins = "http://localhost:5173")
public class FeedbackController {

    private final FeedbackServiceImpl feedbackService;

    private final ModelMapper modelMapper;

    private final QuestionServiceImpl questionService;

    private final FeedbackPackageServiceImpl feedbackPackageService;

    private final ResponseServiceImpl responseService;

    private final UserServiceImpl userService;

    private static final String FEEDBACK_STATUS_COMPLETED = "COMPLETED";

    @Autowired
    public FeedbackController(FeedbackServiceImpl feedbackService, ModelMapper modelMapper, QuestionServiceImpl questionService, FeedbackPackageServiceImpl feedbackPackageService, ResponseServiceImpl responseService, UserServiceImpl userService) {
        this.feedbackService = feedbackService;
        this.modelMapper = modelMapper;
        this.questionService = questionService;
        this.feedbackPackageService = feedbackPackageService;
        this.responseService = responseService;
        this.userService = userService;
    }

    @GetMapping
    public List<FeedbackDTO> getAllFeedbacks() {
        return feedbackService.findAll()
                .stream()
                .map(this::convertToFeedbackDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("page/{indexOfPage}")
    public List<FeedbackDTO> getFeedbacksByPage(@PathVariable("indexOfPage") int indexOfPage) {
        return feedbackService.findFeedbacksByPage(indexOfPage)
                .stream()
                .map(this::convertToFeedbackDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<FeedbackDTO> getUserFeedbacks(@PathVariable("userId") int userId) throws UserException {
        User user = userService.findById(userId);

        return user.getFeedbacks()
		        .stream()
                .map(this::convertToFeedbackDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/package/{feedbackPackageId}")
    public List<FeedbackDTO> getFeedbacks(@PathVariable("feedbackPackageId") int feedbackPackageId) throws FeedbackPackageException {
        FeedbackPackage feedbackPackage = feedbackPackageService.findById(feedbackPackageId);

        return feedbackPackage.getFeedbacks()
                .stream()
                .map(this::convertToFeedbackDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/feedbacks")
    public List<FeedbackDTO> getUsersFeedbacks(@PathVariable("userId") int userId) throws UserException {
        User user = userService.findById(userId);

        List<Feedback> feedbacks = new ArrayList<>();
        for (FeedbackPackage feedbackPackage: user.getPackages()) {
            feedbacks.addAll(feedbackPackage.getFeedbacks());
        }

        List<Feedback> completedFeedbacks = feedbacks.stream()
                .filter(feedback -> Objects.equals(feedback.getStatus().getName(), FEEDBACK_STATUS_COMPLETED))
                .toList();

        return completedFeedbacks.stream()
                .map(this::convertToFeedbackDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FeedbackDTO getFeedback(@PathVariable("id") int id) throws FeedbackException {
        return convertToFeedbackDTO(feedbackService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid FeedbackDTO feedbackDTO, BindingResult bindingResult) throws QuestionException, FeedbackException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new FeedbackException(message.toString());
        }

        Feedback feedback = convertToFeedback(feedbackDTO);
        List<Response> responses = feedbackDTO.getResponses();

        for (Response response: responses) {
            response.setQuestion(questionService.findByText(response.getQuestion().getText()));
        }

        feedback.setResponses(responses);
        feedbackService.save(feedback);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid FeedbackDTO feedbackDTO, @PathVariable("id") int id,
                                             BindingResult bindingResult) throws FeedbackException, QuestionException {

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new FeedbackException(message.toString());
        }

        Feedback feedback = feedbackService.findById(id);
        List<Response> responses = feedbackDTO.getResponses();

        for (Response response: responses) {
            response.setQuestion(questionService.findByText(response.getQuestion().getText()));
            response.setFeedback(feedback);
            responseService.save(response);
        }

        feedback.setResponses(responses);
        feedbackService.update(feedback, id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

        feedbackService.deleteById(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private Feedback convertToFeedback(FeedbackDTO feedbackDTO) {
        return modelMapper.map(feedbackDTO, Feedback.class);
    }

    private FeedbackDTO convertToFeedbackDTO(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDTO.class);
    }
}
