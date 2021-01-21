package info.pionas.rental.domain.apartmentbookinghistory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * @author Adi
 */
@RequiredArgsConstructor
@Getter
@Embeddable
public class BookingPeriod {

    private final LocalDate periodStart;
    private final LocalDate periodEnd;

}
