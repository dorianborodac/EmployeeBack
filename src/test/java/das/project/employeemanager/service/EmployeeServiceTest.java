package das.project.employeemanager.service;

import das.project.employeemanager.dto.EmployeeDTO;
import das.project.employeemanager.model.Employee;
import das.project.employeemanager.repo.EmployeeRepo;
import das.project.employeemanager.service.imp.EmployeeServiceImp;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserters;
import static org.mockito.BDDMockito.given;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
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

    @Autowired
    @InjectMocks
    private EmployeeServiceImp employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;

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

        EmployeeDTO employee = new EmployeeDTO();
        employee.setName("Buna Ziua");
        employee.setEmail("dori@gmail.com");
        employee.setPhone("69-070-142");
        employee.setJobTitle("Java Senior");
        employee.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar3.png");

        EmployeeDTO savedEmployee = this.webTestClient
                .post()
                .uri(serverURL + "/employee/")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EmployeeDTO.class)
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
    void gotAllEmployees() {
//        List<EmployeeDTO> allEmployee = employeeRepo.findAll();
//        assertThat(allEmployee.size()).isGreaterThanOrEqualTo(1);
        EmployeeDTO employee3 = new EmployeeDTO();
        employee3.setId(3L);
        employee3.setName("Pavel Borodac");
        employee3.setPhone("24-333-222");
        employee3.setEmail("pavel@gmail.com");
        employee3.setJobTitle("Java Junior");
        employee3.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setId(1L);
        employee1.setName("Pavel Borodac");
        employee1.setPhone("24-333-222");
        employee1.setEmail("pavel@gmail.com");
        employee1.setJobTitle("Java Junior");
        employee1.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");

//        given(employeeService.findAllEmployees()).willReturn(List.of(employee3, employee1));
//        List<EmployeeDTO> allEmployeeList = employeeService.findAllEmployees();
//        assertThat(allEmployeeList.size()).isGreaterThanOrEqualTo(1);
//        assertThat(allEmployeeList).containsExactly(employee3,employee1);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        employeeDTOList.add(employee3);
        employeeDTOList.add(employee1);



    }

    @Test
    void updateEmployee() {
        EmployeeDTO employee3 = new EmployeeDTO();
        employee3.setId(3L);
        employee3.setName("Pavel Borodac");
        employee3.setPhone("24-333-222");
        employee3.setEmail("pavel@gmail.com");
        employee3.setJobTitle("Java Junior");
        employee3.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");

        EmployeeDTO savedEmployee = this.webTestClient
                .post()
                .uri(serverURL + "/employee/")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee3))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EmployeeDTO.class)
                .returnResult()
                .getResponseBody();
        assertNotNull(savedEmployee);

        EmployeeDTO employee3update = new EmployeeDTO();
        employee3update.setId(savedEmployee.getId());
        employee3update.setName("Borodac Dorian");
        employee3update.setPhone("24-323-222");
        employee3update.setEmail("dorian@gmail.com");
        employee3update.setJobTitle("Java Suunior");
        employee3update.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar3.png");

        EmployeeDTO updateEmployee = this.webTestClient
                .put()
                .uri(serverURL + "/employee/")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee3update))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EmployeeDTO.class)
                .returnResult()
                .getResponseBody();
        assertNotNull(updateEmployee);

        EmployeeDTO getUni = this.webTestClient
                .get()
                .uri(serverURL + "/employee/find/" + updateEmployee.getId())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EmployeeDTO.class)
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
        Assertions.assertEquals(employee3update.getId(), updateEmployee.getId());
        Assertions.assertEquals(employee3update.getName(), updateEmployee.getName());
        Assertions.assertEquals(employee3update.getPhone(), updateEmployee.getPhone());
        Assertions.assertEquals(employee3update.getEmail(), updateEmployee.getEmail());
        Assertions.assertEquals(employee3update.getJobTitle(), updateEmployee.getJobTitle());
        Assertions.assertEquals(employee3update.getImageUrl(), updateEmployee.getImageUrl());
    }

    @Test
    void findEmployeeById() {
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setId(1L);
        employee1.setName("Pavel Borodac");
        employee1.setPhone("24-333-222");
        employee1.setEmail("pavel@gmail.com");
        employee1.setJobTitle("Java Junior");
        employee1.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");

        EmployeeDTO savedEmployee = this.webTestClient
                .post()
                .uri(serverURL + "/employee/")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee1))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EmployeeDTO.class)
                .returnResult()
                .getResponseBody();
        assertNotNull(savedEmployee);

        EmployeeDTO getUni = this.webTestClient
                .get()
                .uri(serverURL + "/employee/find/" + savedEmployee.getId())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EmployeeDTO.class)
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
        EmployeeDTO employee3 = new EmployeeDTO();
        employee3.setId(3L);
        employee3.setName("Pavel Borodac");
        employee3.setPhone("24-333-222");
        employee3.setEmail("pavel@gmail.com");
        employee3.setJobTitle("Java Junior");
        employee3.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");
        EmployeeDTO savedEmployee = this.webTestClient
                .post()
                .uri(serverURL + "/employee/")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(BodyInserters.fromValue(employee3))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(EmployeeDTO.class)
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
