package ie.akisialiova.addressbook.service;

import ie.akisialiova.addressbook.model.Address;

public interface AddressService {

    /**
     * Add an address to a person
     *
     * @param personId id of person to add address
     * @param address  address to add
     * @return id of added address
     * @throws {@NoSuchElementException} if a person with provided personId doesn't exists
     */
    Long save(Long personId, Address address);

    /**
     * Update an address for a person
     *
     * @param personId  id of person to update address
     * @param addressId id of address to update
     * @param address   address with updates
     * @throws {@NoSuchElementException} if a person with provided personId doesn't exists
     */
    void update(Long personId, Long addressId, Address address);

    /**
     * Delete an address for a person
     *
     * @param personId  id of person to delete address
     * @param addressId id of address to delete
     * @throws {@NoSuchElementException} if a person/address with provided personId-addressId  doesn't exists
     */
    void delete(Long personId, Long addressId);
}
