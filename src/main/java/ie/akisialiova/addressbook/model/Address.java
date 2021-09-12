package ie.akisialiova.addressbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Address entity
 * Person can have multiple addresses with different postal codes
 * Postal code and person are mandatory params
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address", uniqueConstraints = @UniqueConstraint(columnNames = {"person_id", "postal_code"}))
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String state;

    @Column(name = "postal_code", nullable = false)
    @NotBlank(message = "postal code is mandatory")
    private String postalCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public void setPerson(Person person) {
        this.person = person;
    }
}
