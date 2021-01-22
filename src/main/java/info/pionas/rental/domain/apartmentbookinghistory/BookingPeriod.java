package info.pionas.rental.domain.apartmentbookinghistory;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class BookingPeriod {

    private LocalDate periodStart;
    private LocalDate periodEnd;

}
