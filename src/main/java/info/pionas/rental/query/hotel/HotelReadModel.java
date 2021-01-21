package info.pionas.rental.query.hotel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @author Adi
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "HOTEL")
public class HotelReadModel {

    @Id
    private UUID id;
    private String name;
    private String street;
    private String postalCode;
    private String buildingNumber;
    private String city;
    private String country;
}
