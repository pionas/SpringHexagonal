package info.pionas.rental.query.hotel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
@AllArgsConstructor
//@Entity
@Getter
@Table(name = "HOTEL")
public class HotelReadModel {

    @Id
    private String id;
    private final String name;
    private final String street;
    private final String postalCode;
    private final String buildingNumber;
    private final String city;
    private final String country;
}
