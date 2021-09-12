package ie.akisialiova.addressbook.service;


import ie.akisialiova.addressbook.model.Person;
import ie.akisialiova.addressbook.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = PersonServiceImpl.class)
@ActiveProfiles("test")
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService service;

    @Test
    public void whenSavePerson_thenReturnIdOfNewPerson() {
        // given
        Person personToSave = new Person("Bill", "Gates");
        Person personAfterSave = new Person(1L, "Bill", "Gates");
        given(personRepository.save(personToSave)).willReturn(personAfterSave);
        // when
        Long personId = service.save(personToSave);
        // then
        then(personRepository).should().save(personToSave);
        assertEquals(personAfterSave.getId(), personId);
    }

    @Test
    public void whenUpdateExistingPerson_thenOk() {
        // given
        Person updatedPerson = new Person("Bill", "Gates");
        given(personRepository.update(1L, updatedPerson.getFirstName(), updatedPerson.getLastName())).willReturn(1);
        // when
        service.update(1L, updatedPerson);
        // then
        then(personRepository).should().update(1L, updatedPerson.getFirstName(), updatedPerson.getLastName());
    }

    @Test
    public void whenUpdateNonExistingPerson_thenException() {
        // given
        Person updatedPerson = new Person("Bill", "Gates");
        given(personRepository.update(1L, updatedPerson.getFirstName(), updatedPerson.getLastName())).willReturn(0);
        // when
        // then
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            service.update(1L, updatedPerson);
        });
    }

    @Test
    public void whenDeleteExistingPerson_thenOk() {
        // given
        given(personRepository.findById(1L)).willReturn(Optional.of(new Person()));
        // when
        service.delete(1L);
        // then
        then(personRepository).should().deleteById(1L);
    }

    @Test
    public void whenDeleteNonExistingPerson_thenException() {
        // given
        given(personRepository.findById(1L)).willReturn(Optional.empty());
        // when
        // then
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            service.delete(1L);
        });
    }
}
