package info.pionas.rental.query.apartment;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "APARTMENT")
public class ApartmentReadModel {

    @Id
    private String id;

    private final String ownerId;

    private final String street;
    private final String postalCode;
    private final String houseNumber;
    private final String apartmentNumber;
    private final String city;
    private final String country;

    @OneToMany
    private final List<RoomReadModel> rooms;

    private final String description;

}
