package das.project.employeemanager.service;

import das.project.employeemanager.exception.UserNotFoundException;
import das.project.employeemanager.model.Employee;
import das.project.employeemanager.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    //take employee, and create employee in database
    public Employee addEmployee(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    //return list of employee
    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    //update the employees
    public Employee updateEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    //find and employee by id (QUERY METHOD IN SPRING)
    public Employee findEmployeeById(Long id) {
        return employeeRepo.findEmployeeById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    //delete and employee byId (QUERY METHOD)
    public void deleteEmployee(Long id) {
        employeeRepo.deleteEmployeeById(id);
    }
}
