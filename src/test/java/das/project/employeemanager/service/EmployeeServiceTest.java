package das.project.employeemanager.service;

import das.project.employeemanager.model.Employee;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserters;


import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "PT1M")//30 seconds
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class EmployeeServiceTest {

    @Test
    void contextLoads() {

    }

    private String serverURL;

    @LocalServerPort
    private int port;

    private final WebTestClient webTestClient;

    @Mock
    private HttpServletRequest request;

    @BeforeAll
    public void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        serverURL = String.format("%s:%s", "localhost", port);
    }

    @Test
    void addEmployee() {
        Employee employee = new Employee();
        employee.setName("Buna Ziua");
        employee.setEmail("dori@gmail.com");
        employee.setPhone("69-070-142");
        employee.setJobTitle("Java Senior");
        employee.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar3.png");

        Employee savedEmployee = this.webTestClient
                .post()
                .uri(serverURL + "/employee/add/")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Employee.class)
                .returnResult()
                .getResponseBody();
        assertNotNull(savedEmployee);

        HttpStatus deleteUni = this.webTestClient
                .delete()
                .uri(serverURL + "/employee/delete/" + savedEmployee.getId())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(HttpStatus.class)
                .returnResult()
                .getResponseBody();


        assertEquals(employee.getName(), savedEmployee.getName());
        assertEquals(employee.getEmail(), savedEmployee.getEmail());
        assertEquals(employee.getPhone(), savedEmployee.getPhone());
        assertEquals(employee.getJobTitle(), savedEmployee.getJobTitle());
        assertEquals(employee.getImageUrl(), savedEmployee.getImageUrl());

    }

    @Test
    void findAllEmployees() {
        Employee employee = new Employee();
        employee.setName("Buna Ziua");
        employee.setEmail("dori@gmail.com");
        employee.setPhone("69-070-142");
        employee.setJobTitle("Java Senior");
        employee.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar3.png");
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void findEmployeeById() {
    }

    @Test
    void deleteEmployee() {
    }
}
