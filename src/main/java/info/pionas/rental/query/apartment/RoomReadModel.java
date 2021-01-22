package info.pionas.rental.query.apartment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Embeddable
class RoomReadModel {

    private String name;
    private Double size;
}
