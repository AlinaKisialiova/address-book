package ie.akisialiova.addressbook.repository;

import ie.akisialiova.addressbook.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Transactional
    @Modifying
    @Query("update Person p set p.firstName = :firstName, p.lastName = :lastName where p.id = :id")
    void update(@Param("id") Long personId, @Param("firstName") String firstName, @Param("lastName") String lastName);
}
