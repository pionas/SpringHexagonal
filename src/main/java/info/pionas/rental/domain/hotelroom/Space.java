package info.pionas.rental.domain.hotelroom;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
@SuppressWarnings("PMD.UnusedPrivateField")
public class Space {

    private String name;
    @Embedded
    private SquareMeter squareMeter;
}
