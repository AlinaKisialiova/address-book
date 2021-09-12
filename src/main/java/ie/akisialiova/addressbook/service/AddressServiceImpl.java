package ie.akisialiova.addressbook.service;

import ie.akisialiova.addressbook.model.Address;
import ie.akisialiova.addressbook.model.Person;
import ie.akisialiova.addressbook.repository.AddressRepository;
import ie.akisialiova.addressbook.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final PersonRepository personRepository;

    @Override
    public Long save(Long personId, Address address) {
        Person person = personRepository.findById(personId).orElseThrow();
        person.addAddress(address);
        Person savedPerson = personRepository.save(person);
        Address savedAddress = savedPerson.getAddresses().stream()
                .filter(a -> a.getPostalCode().equals(address.getPostalCode()))
                .findAny()
                .orElseThrow();
        return savedAddress.getId();
    }

    @Override
    public void update(Long personId, Long addressId, Address address) {
        Person person = personRepository.findById(personId).orElseThrow();
        Address existingAddress = person.getAddresses()
                .stream()
                .filter(a -> a.getId().equals(addressId))
                .findAny()
                .orElseThrow();
        Address updated = Address.builder()
                .id(existingAddress.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .person(existingAddress.getPerson())
                .build();
        addressRepository.save(updated);
    }

    @Override
    public void delete(Long personId, Long addressId) {
        Person person = personRepository.findById(personId).orElseThrow();
        Address address = person.getAddresses()
                .stream()
                .filter(a -> a.getId().equals(addressId))
                .findAny()
                .orElseThrow();
        person.removeAddress(address);
        personRepository.save(person);
    }
}
