package ie.akisialiova.addressbook;

import ie.akisialiova.addressbook.controller.AddressController;
import ie.akisialiova.addressbook.controller.PersonController;
import ie.akisialiova.addressbook.model.Address;
import ie.akisialiova.addressbook.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = AddressBookApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = {"/populateDB.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = {"/cleanUpDb.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AddressBookIntegrationTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void whenAddPerson_thenCorrectResponse() {
        Person person = new Person("Ben", "Afflec");
        ResponseEntity<Long> response = this.restTemplate
                .postForEntity("http://localhost:" + port + PersonController.PERSONS_PATH, person, Long.class);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void whenGetAll_thenCorrectResponse() {
        ResponseEntity<List> response = restTemplate
                .getForEntity("http://localhost:" + port + PersonController.PERSONS_PATH + "?page=0&size=3", List.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void whenGetTotal_thenCorrectResponse() {
        ResponseEntity<Long> response = restTemplate.getForEntity("http://localhost:" + port + PersonController.PERSONS_PATH + "/total", Long.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(Long.valueOf(5L), response.getBody().longValue());
    }

    @Test
    public void whenPostAddress_thenCorrectResponse() {
        Address address = Address.builder()
                .city("Cork")
                .street("Market street")
                .postalCode("x1x2")
                .build();
        final String addressPath = (AddressController.ADDRESSES_PATH).replace("{personId}", "22");
        ResponseEntity<Long> response = restTemplate
                .postForEntity("http://localhost:" + port + addressPath, address, Long.class);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void whenPostAddressToNonExistingPerson_thenCorrectResponse() {
        Address address = Address.builder()
                .city("Cork")
                .street("Market street")
                .postalCode("x1x2")
                .build();
        final String addressPath = (AddressController.ADDRESSES_PATH).replace("{personId}", "10");
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + addressPath, address, String.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void whenPostExistingAddress_thenConflictResponse() {
        Address address = Address.builder()
                .city("Cork")
                .street("Market street")
                .postalCode("x1z2")
                .build();
        final String addressPath = (AddressController.ADDRESSES_PATH).replace("{personId}", "21");
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + addressPath, address, String.class);
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCodeValue());
    }

    @Test
    public void whenPutIncorrectAddress_thenCorrectResponse() {
        Address address = Address.builder()
                .city("Cork")
                .street("Market street")
                .build();
        final String addressPath = (AddressController.ADDRESSES_PATH).replace("{personId}", "1");
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + addressPath + "/3", HttpMethod.PUT, new HttpEntity<>(address), Void.class);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }
}
