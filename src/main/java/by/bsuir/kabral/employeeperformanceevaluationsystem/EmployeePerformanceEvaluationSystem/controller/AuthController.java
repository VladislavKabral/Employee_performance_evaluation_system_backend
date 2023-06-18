package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.controller;

import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.AuthorizedUserDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.dto.UserDTO;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.LoginCredentials;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model.User;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.security.JWTUtil;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.service.UserServiceImpl;
import by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.util.exception.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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
    public AuthorizedUserDTO registerHandler(@RequestBody UserDTO userDTO) throws UserException {
        userService.save(convertToUser(userDTO));
        User user = userService.findByEmail(userDTO.getEmail());
        String token = jwtUtil.generateToken(userDTO.getEmail());
        return new AuthorizedUserDTO(token, user.getId(), user.getRole().getName());
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @PostMapping("/login")
    public AuthorizedUserDTO loginHandler(@RequestBody LoginCredentials body) throws UserException {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

        } catch (BadCredentialsException e){
            throw new UserException("Invalid credentials.");
        }

        String token = jwtUtil.generateToken(body.getEmail());
        User user = userService.findByEmail(body.getEmail());

        return new AuthorizedUserDTO(token, user.getId(), user.getRole().getName());
    }
}
