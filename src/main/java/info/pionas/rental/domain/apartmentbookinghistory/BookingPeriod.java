package info.pionas.rental.domain.apartmentbookinghistory;

import java.time.LocalDate;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
