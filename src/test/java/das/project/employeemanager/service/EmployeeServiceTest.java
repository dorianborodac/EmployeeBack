package das.project.employeemanager.service;

import das.project.employeemanager.model.Employee;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.matcher.ElementMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        Employee employee3 = new Employee();
        employee3.setName("Pavel Borodac");
        employee3.setPhone("24-333-222");
        employee3.setEmail("pavel@gmail.com");
        employee3.setJobTitle("Java Junior");
        employee3.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");

        Employee employee4 = new Employee();
        employee4.setName("Andrei Borodac");
        employee4.setPhone("23-333-622");
        employee4.setEmail("andrei@gmail.com");
        employee4.setJobTitle("Java Middle");
        employee4.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar6.png");

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee3);
        employeeList.add(employee4);




        List<Employee> savedEmployees = new ArrayList<>();
        employeeList.forEach(employee -> {
            savedEmployees.add(webTestClient
                    .post()
                    .uri(serverURL + "/employee/add")
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .body(BodyInserters.fromValue(employee))
                    .exchange()
                    .expectStatus()
                    .is2xxSuccessful()
                    .expectBody(Employee.class)
                    .returnResult()
                    .getResponseBody());
        });


        List<Employee> gotEmployees = new ArrayList<>();

//        List<Employee> gotEmployees = this.webTestClient
//                .get()
//                .uri(serverURL + "/employee/all")
//                .exchange()
//                .expectStatus()
//                .is2xxSuccessful()
//                .expectBodyList(Employee.class)
//                .returnResult()
//                .getResponseBody();
//        assertNotNull(gotEmployees);



//        HttpStatus deleteUni = this.webTestClient
//                .delete()
//                .uri(serverURL + "/employee/delete/" + savedEmployees)
//                .accept(APPLICATION_JSON)
//                .exchange()
//                .expectStatus().is2xxSuccessful()
//                .expectBody(HttpStatus.class)
//                .returnResult()
//                .getResponseBody();
//        assertThat(employee3.getId().equals(getAll.getId()));
//        assertThat(employee4.getId().equals(getAll.getId()));
//        assertEquals(savedEmployee.getId(), getAll.getId());
//        assertEquals(savedEmployee.getName(), getAll.getName());
//        assertEquals(savedEmployee.getPhone(), getAll.getPhone());
//        assertEquals(savedEmployee.getEmail(), getAll.getEmail());
//        assertEquals(savedEmployee.getJobTitle(), getAll.getJobTitle());
//        assertEquals(savedEmployee.getImageUrl(), getAll.getImageUrl());
         // assertThat(getValidEmployee().size(), );
    }

    @Test
    void updateEmployee() {
        Employee employee3 = new Employee();
        employee3.setName("Pavel Borodac");
        employee3.setPhone("24-333-222");
        employee3.setEmail("pavel@gmail.com");
        employee3.setJobTitle("Java Junior");
        employee3.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");

        Employee savedEmployee = this.webTestClient
                .post()
                .uri(serverURL + "/employee/add")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee3))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Employee.class)
                .returnResult()
                .getResponseBody();
        assertNotNull(savedEmployee);

        Employee employee3update = new Employee();
        employee3update.setName("Borodac Dorian");
        employee3update.setPhone("24-323-222");
        employee3update.setEmail("pavel@gmail.com");
        employee3update.setJobTitle("Java Junior");
        employee3update.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");

        Employee updateEmployee = this.webTestClient
                .put()
                .uri(serverURL + "/employee/update/")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee3update))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Employee.class)
                .returnResult()
                .getResponseBody();
        assertNotNull(updateEmployee);

        HttpStatus deleteUni = this.webTestClient
                .delete()
                .uri(serverURL + "/employee/delete/" + savedEmployee.getId())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(HttpStatus.class)
                .returnResult()
                .getResponseBody();

//        assertEquals(savedEmployee.getName(), updateEmployee.setName("Borodac Dorian");)

        Assertions.assertEquals(savedEmployee.getName(), employee3update.getName());
        Assertions.assertEquals(savedEmployee.getPhone(), employee3update.getPhone());
        Assertions.assertEquals(savedEmployee.getEmail(), employee3update.getEmail());
        Assertions.assertEquals(savedEmployee.getJobTitle(), employee3update.getJobTitle());
        Assertions.assertEquals(savedEmployee.getImageUrl(), employee3update.getImageUrl());
    }

    @Test
    void findEmployeeById() {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Pavel Borodac");
        employee1.setPhone("24-333-222");
        employee1.setEmail("pavel@gmail.com");
        employee1.setJobTitle("Java Junior");
        employee1.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");

        Employee savedEmployee = this.webTestClient
                .post()
                .uri(serverURL + "/employee/add")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee1))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Employee.class)
                .returnResult()
                .getResponseBody();
        assertNotNull(savedEmployee);

        Employee getUni = this.webTestClient
                .get()
                .uri(serverURL + "/employee/find/" + savedEmployee.getId())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Employee.class)
                .returnResult()
                .getResponseBody();
        assertNotNull(getUni);

        HttpStatus deleteUni = this.webTestClient
                .delete()
                .uri(serverURL + "/employee/delete/" + savedEmployee.getId())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(HttpStatus.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(savedEmployee.getId(), getUni.getId());
        Assertions.assertEquals(savedEmployee.getName(), getUni.getName());
        Assertions.assertEquals(savedEmployee.getPhone(), getUni.getPhone());
        Assertions.assertEquals(savedEmployee.getEmail(), getUni.getEmail());
        Assertions.assertEquals(savedEmployee.getJobTitle(), getUni.getJobTitle());
        Assertions.assertEquals(savedEmployee.getImageUrl(), getUni.getImageUrl());
    }

    @Test
    void deleteEmployee() {
        Employee employee3 = new Employee();
        employee3.setId(3L);
        employee3.setName("Pavel Borodac");
        employee3.setPhone("24-333-222");
        employee3.setEmail("pavel@gmail.com");
        employee3.setJobTitle("Java Junior");
        employee3.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");
        Employee savedEmployee = this.webTestClient
                .post()
                .uri(serverURL + "/employee/add")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee3))
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
    }


}
