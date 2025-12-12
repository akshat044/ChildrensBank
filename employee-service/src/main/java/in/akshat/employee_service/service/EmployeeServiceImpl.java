package in.akshat.employee_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.akshat.employee_service.client.BranchClient;
import in.akshat.employee_service.dto.EmployeeUpdateDto;
import in.akshat.employee_service.entity.Employee;
import in.akshat.employee_service.exception.EmpAlreadyExistsException;
import in.akshat.employee_service.exception.NoSuchEmpExistsException;
import in.akshat.employee_service.mapper.EmployeeMapper;
import in.akshat.employee_service.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private BranchClient branchClient;

    @Override
    public Employee saveEmployee(Employee employee) {
        // email unique
        if (checkEmailExists(employee.getEmail())) {
            throw new EmpAlreadyExistsException("Employee already exists with this email");
        }
        // phone unique (add repo method if you want strict check)
        // if (checkPhoneExists(employee.getPhoneNo())) { ... }

        // branch must exist
        boolean branchExists = branchClient.checkBranchExists(employee.getBranchId());
        if (!branchExists) {
            throw new NoSuchEmpExistsException("Branch not found with this id so cannot add employee");
        }

        // employeeId is supplied at create time, immutable later (do not touch here)
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeUpdateDto dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchEmpExistsException("Employee not found with id: " + id));

        // If email is changing, ensure uniqueness against other employees
        if (dto.getEmail() != null && !dto.getEmail().equalsIgnoreCase(existing.getEmail())) {
            // Prefer repository method existsByEmailAndIdNot(...) to avoid loading
            Optional<Employee> byEmail = employeeRepository.findByEmail(dto.getEmail());
            if (byEmail.isPresent() && !byEmail.get().getId().equals(id)) {
                throw new EmpAlreadyExistsException("Another employee already uses this email");
            }
        }

        // If branchId is changing, validate the branch exists
        if (dto.getBranchId() != null && !dto.getBranchId().equals(existing.getBranchId())) {
            boolean branchExists = branchClient.checkBranchExists(dto.getBranchId());
            if (!branchExists) {
                throw new NoSuchEmpExistsException("Target branch not found, cannot update employee");
            }
        }

        // Merge non-null fields. IMPORTANT: employeeId remains untouched (immutable)
        EmployeeMapper.merge(existing, dto); // for adding in existing only those feild that we really wanted to add 

        return employeeRepository.save(existing);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchEmpExistsException("No such employee found with id: " + id));
    }

    @Override
    public void deleteEmployeeById(Long id) {
        if (!checkEmployeeExistsById(id)) {
            throw new NoSuchEmpExistsException("Employee does not exist so cannot delete");
        }
        employeeRepository.deleteById(id);
    }

    // ------- helpers -------
    public boolean checkEmployeeExistsById(Long id) {
        return employeeRepository.findById(id).isPresent();
    }

    public boolean checkEmailExists(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }

    // Optional if you want phone uniqueness too:
    // public boolean checkPhoneExists(String phone) {
    //     return employeeRepository.findByPhoneNo(phone).isPresent();
    // }
}
