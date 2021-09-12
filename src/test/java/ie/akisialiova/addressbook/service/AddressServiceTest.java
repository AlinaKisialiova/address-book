package ie.akisialiova.addressbook.service;

import ie.akisialiova.addressbook.model.Address;
import ie.akisialiova.addressbook.model.Person;
import ie.akisialiova.addressbook.repository.AddressRepository;
import ie.akisialiova.addressbook.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(classes = AddressServiceImpl.class)
@ActiveProfiles("test")
public class AddressServiceTest {

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private AddressService addressService;

    @Test
    public void whenSaveAddress_thenReturnIdOfNewAddress() {
        // given
        Address address = Address.builder().id(2L).postalCode("123").street("main").build();
        Person personBeforeSave = new Person(1L, "Bill", "Gates");
        Person personAfterSave = new Person(1L, "Bill", "Gates", List.of(address));
        given(personRepository.findById(1L)).willReturn(Optional.of(personBeforeSave));
        given(personRepository.save(personBeforeSave)).willReturn(personAfterSave);
        // when
        Long addressId = addressService.save(1L, address);
        // then
        then(personRepository).should().findById(1L);
        then(personRepository).should().save(personBeforeSave);
        assertEquals(address.getId(), addressId);
    }

    @Test
    public void whenSaveAddressForNotExistingPerson_thenException() {
        // given
        Address address = Address.builder().postalCode("123").street("main").build();
        given(personRepository.findById(1L)).willReturn(Optional.empty());
        // when
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            addressService.save(1L, address);
        });
    }

    @Test
    public void whenUpdateAddressForNotExistingPerson_thenException() {
        // given
        Address address = Address.builder().postalCode("123").street("main").build();
        given(personRepository.findById(1L)).willReturn(Optional.empty());
        // when
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            addressService.update(1L, 2L, address);
        });
    }

    @Test
    public void whenUpdateNonExistingAddress_thenException() {
        // given
        Address address = Address.builder().postalCode("123").street("main").build();
        Address existingAddress = Address.builder().id(3L).postalCode("xx1").build();
        Person person = new Person(1L, "Bill", "Gates", List.of(existingAddress));
        given(personRepository.findById(1L)).willReturn(Optional.of(person));
        // when
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            addressService.update(1L, 2L, address);
        });
    }

    @Test
    public void whenDeleteAddressForNonExistingPerson_thenException() {
        // given
        Address existingAddress = Address.builder().id(3L).postalCode("xx1").build();
        given(personRepository.findById(1L)).willReturn(Optional.empty());
        // when
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            addressService.delete(1L, 2L);
        });
    }

    @Test
    public void whenDeleteNonExistingAddressForPerson_thenException() {
        // given
        Address existingAddress = Address.builder().id(3L).postalCode("xx1").build();
        Person person = new Person(1L, "Bill", "Gates", List.of(existingAddress));
        given(personRepository.findById(1L)).willReturn(Optional.of(person));
        // when
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            addressService.delete(1L, 2L);
        });
    }
}
