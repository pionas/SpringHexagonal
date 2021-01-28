package info.pionas.rental.domain.period;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class Period {

    private LocalDate periodStart;
    private LocalDate periodEnd;

    public List<LocalDate> asDays() {
        return periodStart.datesUntil(periodEnd.plusDays(1)).collect(Collectors.toList());
    }

    public LocalDate getStart() {
        return periodStart;
    }

    public void setStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getEnd() {
        return periodEnd;
    }

    public void setEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }
}
