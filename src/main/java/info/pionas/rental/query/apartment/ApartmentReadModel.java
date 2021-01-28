package info.pionas.rental.query.apartment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "APARTMENT")
public class ApartmentReadModel {

    @Id
    @GeneratedValue
    private UUID id;

    private String ownerId;
    private String street;
    private String postalCode;
    @Column(name = "house_number")
    private String houseNumber;
    private String apartmentNumber;
    private String city;
    private String country;
    private String description;

    @ElementCollection
    @CollectionTable(name = "APARTMENT_ROOM", joinColumns = @JoinColumn(name = "APARTMENT_ID"))
    private List<RoomReadModel> rooms;


}
