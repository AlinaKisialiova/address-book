package ie.akisialiova.addressbook.controller;

import ie.akisialiova.addressbook.model.Address;
import ie.akisialiova.addressbook.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {AddressController.ADDRESSES_PATH},
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

    public static final String ADDRESSES_PATH = PersonController.PERSONS_PATH + "/{personId}/addresses";

    private final AddressService service;

    @PostMapping
    public ResponseEntity<Long> addAddress(@PathVariable Long personId, @Valid @RequestBody Address address) {
        Long addressId = service.save(personId, address);
        return new ResponseEntity<>(addressId, HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Void> editAddress(@PathVariable Long personId, @PathVariable Long addressId, @RequestBody @Valid Address address) {
        service.update(personId, addressId, address);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        service.delete(personId, addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
