package info.pionas.rental.domain.period;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter(AccessLevel.PRIVATE)
@Embeddable
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class Period {
    private LocalDate periodStart;
    private LocalDate periodEnd;

    public List<LocalDate> asDays() {
        return periodStart.datesUntil(periodEnd.plusDays(1)).collect(Collectors.toList());
    }

    public boolean contains(LocalDate day) {
        return asDays().contains(day);
    }

    public static Period from(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw PeriodException.startAfterEnd(start, end);
        }
        if (start.isBefore(LocalDate.now())) {
            throw PeriodException.startFromPast(start);
        }
        return new Period(start, end);
    }

    public static Period fromStart(LocalDate start) {
        return from(start, start.plusYears(1));
    }

    public boolean coversAllDaysWithin(Period period) {
        return !period.getPeriodStart().isBefore(periodStart) && !period.getPeriodEnd().isAfter(periodEnd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Period period = (Period) o;

        return new EqualsBuilder().append(periodStart, period.periodStart).append(periodEnd, period.periodEnd).isEquals();
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(periodStart).append(periodEnd).toHashCode();
    }

}
