package ie.akisialiova.addressbook.repository;

import ie.akisialiova.addressbook.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
