package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.Response;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ResponseServiceImpl implements ServiceInterface<Response> {

    private final ResponseRepository responseRepository;

    @Autowired
    public ResponseServiceImpl(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    public List<Response> findAll() {
        return responseRepository.findAll();
    }

    @Override
    public Response findById(int id) {
        Optional<Response> response = responseRepository.findById(id);

        return response.orElse(null);
    }

    @Override
    @Transactional
    public void save(Response response) {
        responseRepository.save(response);
    }

    @Override
    @Transactional
    public void update(Response response, int id) {
        response.setId(id);
        responseRepository.save(response);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        responseRepository.deleteById(id);
    }
}
