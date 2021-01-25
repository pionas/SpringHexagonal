package info.pionas.rental.domain.apartment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Getter
@Embeddable
public class Period {

    private final LocalDate start;
    private final LocalDate end;

    List<LocalDate> asDays() {
        return start.datesUntil(end.plusDays(1)).collect(Collectors.toList());
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

        return new EqualsBuilder().append(start, period.start).append(end, period.end).isEquals();
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(start).append(end).toHashCode();
    }
}
