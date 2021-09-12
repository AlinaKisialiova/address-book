package ie.akisialiova.addressbook.repository;

import ie.akisialiova.addressbook.model.Address;
import ie.akisialiova.addressbook.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
@Sql({"/db/populateDB.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

    @Test
    public void whenAddAddress_thenCorrectResult() {
        // given
        Person person = new Person(2L, "jan", "clod");
        Address address = Address.builder()
                .street("irishtown street")
                .city("Athlone")
                .postalCode("x1z2")
                .person(person)
                .build();
        // when
        Address saved = repository.save(address);
        // then
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(address.getStreet(), saved.getStreet());
        assertEquals(address.getCity(), saved.getCity());
        assertEquals(address.getPostalCode(), saved.getPostalCode());
    }
}
