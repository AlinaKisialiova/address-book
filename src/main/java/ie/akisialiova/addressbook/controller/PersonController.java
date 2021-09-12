package ie.akisialiova.addressbook.controller;

import ie.akisialiova.addressbook.model.Person;
import ie.akisialiova.addressbook.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {PersonController.PERSONS_PATH},
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    public static final String PERSONS_PATH = "/v1/persons";

    private final PersonService service;

    @PostMapping
    public ResponseEntity<Long> addPerson(@Valid @RequestBody Person person) {
        Long personId = service.save(person);
        return new ResponseEntity<>(personId, HttpStatus.CREATED);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<Void> editPerson(@PathVariable Long personId, @Valid @RequestBody Person person) {
        service.update(personId, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long personId) {
        service.delete(personId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getListOfPersons(@RequestParam("page") int page, @RequestParam("size") int size, Pageable pageable) {
        List<Person> listOfPersons = service.getListOfPersons(pageable);
        return ResponseEntity.ok(listOfPersons);
    }

    @GetMapping("/total")
    public ResponseEntity<Long> getTotalNumberOfPersons() {
        Long totalNumber = service.getTotalNumber();
        return ResponseEntity.ok(totalNumber);
    }
}
