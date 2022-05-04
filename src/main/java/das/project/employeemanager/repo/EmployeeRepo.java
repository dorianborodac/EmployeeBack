package das.project.employeemanager.repo;

import das.project.employeemanager.model.Employee;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
 //   void deleteEmployeeById(Long id);

    @Override
    void deleteById(Long aLong);

    Optional<Employee> findEmployeeById(Long id);
}
