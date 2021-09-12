package ie.akisialiova.addressbook.service;

import ie.akisialiova.addressbook.model.Person;
import ie.akisialiova.addressbook.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Override
    public Long save(Person person) {
        Person saved = repository.save(person);
        return saved.getId();
    }

    @Override
    public void update(Long personId, Person person) {
        int updatedRows = repository.update(personId, person.getFirstName(), person.getLastName());
        if (updatedRows == 0) {
            throw new NoSuchElementException("There is no person with id=" + personId);
        }
    }

    @Override
    public void delete(Long personId) {
        repository.findById(personId).orElseThrow(() -> new NoSuchElementException("There is no person with id=" + personId));
        repository.deleteById(personId);
    }

    @Override
    public Long getTotalNumber() {
        return repository.count();
    }

    @Override
    public List<Person> getListOfPersons(Pageable pageable) {
        Page<Person> all = repository.findAll(pageable);
        return all.getContent();
    }
}
