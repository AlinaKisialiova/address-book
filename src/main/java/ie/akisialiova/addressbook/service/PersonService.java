package ie.akisialiova.addressbook.service;

import ie.akisialiova.addressbook.model.Person;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {

    /**
     * Add a person
     *
     * @param person  person to add
     * @return id of added person
     * @throws {@NoSuchElementException} if a person with provided personId doesn't exists
     */
    Long save(Person person);

    /**
     * Update a person
     *
     * @param personId  id of person to update
     * @param person   person with updates
     * @throws {@NoSuchElementException} if a person with provided personId doesn't exists
     */
    void update(Long personId, Person person);

    /**
     * Delete an person
     *
     * @param personId  id of person to delete
     * @throws {@NoSuchElementException} if a person with provided personId doesn't exists
     */
    void delete(Long personId);

    /**
     * Get total number of persons in db
     * @return total number
     */
    Long getTotalNumber();

    /**
     * Get list of persons with addresses
     * Pagination is added to prevent db load
     * @param pageable pagination settings
     * @return
     */
    List<Person> getListOfPersons(Pageable pageable);
}
