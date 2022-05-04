package das.project.employeemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import das.project.employeemanager.controllers.EmployeeResource;
import das.project.employeemanager.model.Employee;
import das.project.employeemanager.service.imp.EmployeeServiceImp;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeResource.class)
class EmployeeResourceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImp employeeService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Employee employee1;
    private Employee employee2;


    private final Long employee1EmployeeNumber = 23L;
    private final Long employee2EmployeeNumber = 91L;

    @Before
    public void setup() {
        employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Dorian Borodac");
        employee1.setEmail("dori@gmail.com");
        employee1.setPhone("69-070-142");
        employee1.setJobTitle("Java Senior");
        employee1.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar3.png");

        employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Andrei Borodac");
        employee2.setEmail("andrei@gmail.com");
        employee2.setPhone("69-070-000");
        employee2.setJobTitle("Java Sunior");
        employee2.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");

    }



//    @Test
//    public void gotAllEmployees() throws Exception {
//        given(employeeService.findAllEmployees())
//                .willReturn(Arrays.asList(employee1));
//
//        mockMvc.perform(get("/employee/all")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is(employee1.getName())));
//
//    }

//    @Test
//    public void addSaveEmployee() throws Exception {
//        given(employeeService.addEmployee(any(Employee.class))).willReturn(employee2);
//    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }
}
