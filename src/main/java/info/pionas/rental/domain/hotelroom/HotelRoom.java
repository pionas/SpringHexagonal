package info.pionas.rental.domain.hotelroom;

import info.pionas.rental.domain.booking.Booking;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "HOTEL_ROOM")
@SuppressWarnings("PMD.UnusedPrivateField")
public class HotelRoom {

    @Id
    @GeneratedValue
    private UUID id;
    private String hotelId;
    private int number;
    @ElementCollection
    @CollectionTable(name = "HOTEL_ROOM_SPACE", joinColumns = @JoinColumn(name = "HOTEL_ROOM_ID"))
    private List<Space> spaces;
    private String description;

    public HotelRoom(String hotelId, int number, List<Space> spaces, String description) {
        this.hotelId = hotelId;
        this.number = number;
        this.spaces = spaces;
        this.description = description;
    }

    public Booking book(String tenantId, List<LocalDate> days, HotelRoomEventsPublisher hotelRoomEventsPublisher) {
        hotelRoomEventsPublisher.publishHotelRoomBooked(id(), hotelId, tenantId, days);
        return Booking.hotelRoom(id(), tenantId, days);
    }

    public String id() {
        if (id == null) {
            return null;
        }
        return id.toString();
    }

    public static class Builder {
        private String hotelId;
        private int number;
        private Map<String, Double> spacesDefinition;
        private String description;

        private Builder() {
        }

        public static Builder hotelRoom() {
            return new Builder();
        }

        public Builder withHotelId(String hotelId) {
            this.hotelId = hotelId;
            return this;
        }

        public Builder withNumber(int number) {
            this.number = number;
            return this;
        }

        public Builder withSpacesDefinition(Map<String, Double> spacesDefinition) {
            this.spacesDefinition = spacesDefinition;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public HotelRoom build() {
            List<Space> spaces = spacesDefinition.entrySet().stream()
                    .map(this::asSpace)
                    .collect(toList());

            return new HotelRoom(hotelId, number, spaces, description);

        }

        private Space asSpace(Map.Entry<String, Double> entry) {
            SquareMeter squareMeter = new SquareMeter(entry.getValue());

            return new Space(entry.getKey(), squareMeter);
        }
    }
}
