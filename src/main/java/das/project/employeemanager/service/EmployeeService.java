package das.project.employeemanager.service;

import das.project.employeemanager.dto.EmployeeDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> findAllEmployees();

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO findEmployeeById(Long id);

    HttpStatus deleteEmployeeById(Long id);

}
