package by.bsuir.kabral.employeeperformanceevaluationsystem.EmployeePerformanceEvaluationSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Managers")
@Getter
@Setter
public class Manager implements Serializable {

    @Id
    @Column(name = "manager_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @OneToMany(mappedBy = "manager")
    @JsonManagedReference(value = "manager_user")
    private List<User> users;

    public Manager() {
    }
}
