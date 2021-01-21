package info.pionas.rental.query.hotelroom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Adi
 */
@RequiredArgsConstructor
//@Entity
@Table(name = "HOTEL_ROOM")
@Getter
public class HotelRoomReadModel {

    @Id
    private String hotelRoomId;
    private final String hotelId;
    private final int number;
    @OneToMany
    private final List<SpaceReadModel> spaces;
    private final String description;
}
