package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.UserDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.LoginCredentials;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.security.JWTUtil;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserServiceImpl userService;

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authManager;

    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(UserServiceImpl userService,
                          JWTUtil jwtUtil,
                          AuthenticationManager authManager,
                          ModelMapper modelMapper) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody UserDTO userDTO) {
        userService.save(convertToUser(userDTO));
        String token = jwtUtil.generateToken(userDTO.getEmail());
        return Collections.singletonMap("jwt-token", token);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body) {
        System.out.println(body.getEmail());
        System.out.println(body.getPassword());
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

        } catch (BadCredentialsException e){
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(body.getEmail());

        return Collections.singletonMap("jwt-token", token);
    }
}
