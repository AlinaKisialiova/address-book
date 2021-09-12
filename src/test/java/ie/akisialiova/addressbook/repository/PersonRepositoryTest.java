package ie.akisialiova.addressbook.repository;

import ie.akisialiova.addressbook.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@Sql({"/populateDB.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    public void whenAddPerson_thenCorrectResult() {
        // given
        Person person = new Person("Joan", "Rolling");
        // when
        Person saved = repository.save(person);
        // then
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(person.getFirstName(), person.getFirstName());
        assertEquals(person.getLastName(), person.getLastName());
    }

    @Test
    public void whenUpdatePerson_thenCorrectResult() {
        // given
        Person person = repository.findAll().get(0);
        String newFirstName = "Sara";
        String newSecondName = "Lunt";
        // when
        repository.update(person.getId(), newFirstName, newSecondName);
        // then
        Person updated = repository.findById(person.getId()).get();
        assertNotNull(updated);
    }

    @Test
    public void whenUpdateNotExistingPerson_thenCorrectResult() {
        // when
        repository.update(100L, "Sara", "Lunt");
        // then
        Optional<Person> person = repository.findById(100L);
        assertFalse(person.isPresent());
    }

    @Test
    public void whenDeletePerson_thenCorrectResult() {
        // given
        List<Person> allBefore = repository.findAll();
        // when
        repository.delete(allBefore.get(0));
        // then
        List<Person> allAfter = repository.findAll();
        assertEquals(allBefore.size() - 1, allAfter.size());
        assertFalse(repository.findById(allBefore.get(0).getId()).isPresent());

    }

    @Test
    public void whenFindAll_thenCorrectResult() {
        // when
        Page<Person> all = repository.findAll(PageRequest.of(0, 10));
        // then
        assertNotNull(all);
        assertEquals(5, all.getContent().size());
    }

    @Test
    public void whenCount_thenCorrectTotalNumber() {
        // when
        long totalNumber = repository.count();
        // then
        assertEquals(5, totalNumber);
    }
}
