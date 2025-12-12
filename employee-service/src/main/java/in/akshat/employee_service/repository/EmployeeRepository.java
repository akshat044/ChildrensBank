package in.akshat.employee_service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import in.akshat.employee_service.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    
    Optional<Employee> findByEmail(String email);
}
