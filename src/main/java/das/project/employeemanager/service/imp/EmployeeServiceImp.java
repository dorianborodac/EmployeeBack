package das.project.employeemanager.service.imp;

import das.project.employeemanager.dto.EmployeeDTO;
import das.project.employeemanager.exception.UserNotFoundException;
import das.project.employeemanager.model.Employee;
import das.project.employeemanager.repo.EmployeeRepo;
import das.project.employeemanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {
    private final ModelMapper modelMapper;
    private final EmployeeRepo employeeRepo;

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        employeeDTO.setEmployeeCode(UUID.randomUUID().toString());
        Employee savedEmployee = employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Employee updatedEmployee = employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
        return modelMapper.map(updatedEmployee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO findEmployeeById (Long id) {
        Optional<Employee> optionalEmployee = employeeRepo.findEmployeeById(id);
        if (optionalEmployee.isEmpty()) {
           throw new UserNotFoundException("User by id " + id + " was not found");
        }
        return modelMapper.map(optionalEmployee.get(), EmployeeDTO.class);
    }

    @Override
    public HttpStatus deleteEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepo.findEmployeeById(id);
        if (employee.isPresent()) {
            employeeRepo.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
