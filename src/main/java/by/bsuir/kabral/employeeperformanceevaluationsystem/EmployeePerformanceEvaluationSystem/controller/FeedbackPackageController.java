package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.FeedbackPackageDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.FeedbackPackage;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Form;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.FeedbackPackageServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.FormServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.UserServiceImpl;
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
@RequestMapping("/packages")
public class FeedbackPackageController {

    private final FeedbackPackageServiceImpl feedbackPackageService;

    private final ModelMapper modelMapper;

    private final UserServiceImpl userService;

    private final FormServiceImpl formService;

    @Autowired
    public FeedbackPackageController(FeedbackPackageServiceImpl feedbackPackageService, ModelMapper modelMapper, UserServiceImpl userService, FormServiceImpl formService) {
        this.feedbackPackageService = feedbackPackageService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.formService = formService;
    }

    @GetMapping
    public List<FeedbackPackageDTO> getPackages() {
        return feedbackPackageService.findAll()
                .stream()
                .map(this::convertToFeedbackPackageDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FeedbackPackageDTO getPackage(@PathVariable("id") int id) {
        return convertToFeedbackPackageDTO(feedbackPackageService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid FeedbackPackageDTO feedbackPackageDTO,
                                             BindingResult bindingResult) {

        FeedbackPackage feedbackPackage = convertToFeedbackPackage(feedbackPackageDTO);
        Form form = formService.findByName(feedbackPackageDTO.getForm().getName());
        User user = userService.findByLastnameAndFirstname(feedbackPackageDTO.getTargetUser().getLastname(),
                feedbackPackageDTO.getTargetUser().getFirstname());

        feedbackPackage.setForm(form);
        feedbackPackage.setTargetUser(user);

        feedbackPackageService.save(feedbackPackage);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid FeedbackPackageDTO feedbackPackageDTO,
                                             @PathVariable("id") int id, BindingResult bindingResult) {

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

    private FeedbackPackage convertToFeedbackPackage(FeedbackPackageDTO feedbackPackageDTO) {
        return modelMapper.map(feedbackPackageDTO, FeedbackPackage.class);
    }

    private FeedbackPackageDTO convertToFeedbackPackageDTO(FeedbackPackage feedbackPackage) {
        return modelMapper.map(feedbackPackage, FeedbackPackageDTO.class);
    }
}
