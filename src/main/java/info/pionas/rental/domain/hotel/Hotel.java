package info.pionas.rental.domain.hotel;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@AllArgsConstructor
//@Entity
@Table(name = "HOTEL")
public class Hotel {

    @Id
    @GeneratedValue
    private String id;
    private final String name;
    @Embedded
    private final Address address;

}
