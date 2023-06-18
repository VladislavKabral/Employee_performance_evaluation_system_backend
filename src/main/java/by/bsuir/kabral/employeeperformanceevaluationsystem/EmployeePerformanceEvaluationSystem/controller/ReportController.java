package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.ReportDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.FeedbackPackage;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Report;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.FeedbackPackageServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.ReportService;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.FeedbackPackageException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    private final ReportService reportService;

    private final ModelMapper modelMapper;

    private final FeedbackPackageServiceImpl feedbackPackageService;

    @Autowired
    public ReportController(ReportService reportService,
                            ModelMapper modelMapper,
                            FeedbackPackageServiceImpl feedbackPackageService) {
        this.reportService = reportService;
        this.modelMapper = modelMapper;
        this.feedbackPackageService = feedbackPackageService;
    }

    @GetMapping("/{id}")
    public ReportDTO generateReport(@PathVariable("id") int id) throws FeedbackPackageException {
        FeedbackPackage feedbackPackage = feedbackPackageService.findById(id);

        Report report = reportService.generateReport(feedbackPackage);

        return convertToReportDTO(report);
    }

    private ReportDTO convertToReportDTO(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }

}
