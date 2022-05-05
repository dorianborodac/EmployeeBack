package das.project.employeemanager.service;

import das.project.employeemanager.dto.EmployeeDTO;
import das.project.employeemanager.model.Employee;
import das.project.employeemanager.repo.EmployeeRepo;
import das.project.employeemanager.service.imp.EmployeeServiceImp;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.as;
import static org.mockito.BDDMockito.given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient(timeout = "PT1M")//30 seconds
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class EmployeeServiceTest {

    @Autowired
    private EmployeeServiceImp employeeService;

    @Autowired
    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private EmployeeDTO employeeDTO;

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
    @DisplayName("Test addEmployee() - method that add a new employee")
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
    @DisplayName("Test gotAllEmployees() - return list of all employees")
    void gotAllEmployees() {
        EmployeeDTO employee3 = new EmployeeDTO();
        employee3.setName("Pavel Borodac");
        employee3.setPhone("24-333-222");
        employee3.setEmail("pavel@gmail.com");
        employee3.setJobTitle("Java Junior");
        employee3.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar1.png");

        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setName("Dorian Borodac");
        employee1.setPhone("24-233-112");
        employee1.setEmail("dorian@gmail.com");
        employee1.setJobTitle("Java Juor");
        employee1.setImageUrl("https://www.bootdey.com/app/webroot/img/Content/avatar/avatar2.png");

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        employeeDTOList.add(employee3);
        employeeDTOList.add(employee1);

        Assertions.assertEquals(2, employeeDTOList.size(), "findAllEmployees should return 2 employees");
        Assertions.assertEquals("Dorian Borodac", employee1.getName());
        Assertions.assertEquals("Pavel Borodac", employee3.getName());

        assertThat(employeeDTOList.get(0).getName()).isEqualTo(employee3.getName());
        assertThat(employeeDTOList.get(0).getPhone()).isEqualTo(employee3.getPhone());
        assertThat(employeeDTOList.get(0).getEmail()).isEqualTo(employee3.getEmail());
        assertThat(employeeDTOList.get(0).getJobTitle()).isEqualTo(employee3.getJobTitle());
        assertThat(employeeDTOList.get(0).getImageUrl()).isEqualTo(employee3.getImageUrl());

        assertThat(employeeDTOList.get(1).getName()).isEqualTo(employee1.getName());
        assertThat(employeeDTOList.get(1).getPhone()).isEqualTo(employee1.getPhone());
        assertThat(employeeDTOList.get(1).getEmail()).isEqualTo(employee1.getEmail());
        assertThat(employeeDTOList.get(1).getJobTitle()).isEqualTo(employee1.getJobTitle());
        assertThat(employeeDTOList.get(1).getImageUrl()).isEqualTo(employee1.getImageUrl());
    }

    @Test
    @DisplayName("Test updateEmployee - update a new employee")
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
    @DisplayName("Test findEmployeeById() - find a saved Employee by Id")
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
    @DisplayName("Test deleteEmployee() - delete employee by Id")
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
