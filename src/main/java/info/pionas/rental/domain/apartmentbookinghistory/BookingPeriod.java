package info.pionas.rental.domain.apartmentbookinghistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * @author Adi
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class BookingPeriod {

    private LocalDate periodStart;
    private LocalDate periodEnd;

}
