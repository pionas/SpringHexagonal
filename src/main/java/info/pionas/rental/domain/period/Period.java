package info.pionas.rental.domain.period;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter(AccessLevel.PRIVATE)
@Embeddable
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class Period {
    private LocalDate periodStart;
    private LocalDate periodEnd;

    public static Period from(LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.now())) {
            throw PeriodException.startDateFromPast(start);
        }

        if (start.isAfter(end)) {
            throw PeriodException.startAfterEnd(start, end);
        }

        return new Period(start, end);
    }

    public List<LocalDate> asDays() {
        List<LocalDate> dates = periodStart.datesUntil(periodEnd).collect(toList());
        dates.add(periodEnd);

        return dates;
    }

    public boolean contains(LocalDate day) {
        return asDays().contains(day);
    }

    public boolean isWithin(LocalDate start, LocalDate end) {
        return !periodStart.isBefore(start) && !periodEnd.isAfter(end);
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
