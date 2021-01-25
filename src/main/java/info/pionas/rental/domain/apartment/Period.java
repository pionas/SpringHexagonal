package info.pionas.rental.domain.apartment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Period {

    private final LocalDate start;
    private final LocalDate end;

    List<LocalDate> asDays() {
        return start.datesUntil(end.plusDays(1)).collect(Collectors.toList());
    }

}
