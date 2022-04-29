package das.project.employeemanager.controller;

import static java.util.UUID.randomUUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import das.project.employeemanager.controllers.EmployeeResource;
import das.project.employeemanager.model.Employee;
import das.project.employeemanager.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeResource.class)
class EmployeeResourceTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    private EmployeeService employeeService;

    Employee RECORD_1 = new Employee("Dorain Borodac",
            "dori@gmail.com",
            "Java Senior",
            "22-333-444",
            "https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png",
            randomUUID().toString());

    Employee RECORD_2 = new Employee("Ion Borodac",
            "ion@gmail.com",
            "Java MIddle",
            "22-333-666",
            "https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png",
            randomUUID().toString());


    Employee RECORD_3 = new Employee("Andrei Borodac",
            "andrei@gmail.com",
            "Java Junior",
            "22-333-555",
            "https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png",
            randomUUID().toString());


    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(employeeService.findAllEmployees())
                .thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/employee/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].email", is("ion@gmail.com")));
    }

//    @Test
//    void getEmployeeById() throws Exception {
//        Mockito.when(employeeService.findEmployeeById(RECORD_1.getId()))
//                .thenReturn(id);
//    }

    @Test
    void addEmployee() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }
}
