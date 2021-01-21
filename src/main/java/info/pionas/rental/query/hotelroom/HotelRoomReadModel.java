package info.pionas.rental.query.hotelroom;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * @author Adi
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "HOTEL_ROOM")
public class HotelRoomReadModel {

    @Id
    @GeneratedValue
    private UUID id;
    private String hotelId;
    private int number;
    @ElementCollection
    private List<SpaceReadModel> spaces;
    private String description;
}
