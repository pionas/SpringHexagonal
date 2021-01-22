package info.pionas.rental.infrastructure.rest.api.booking;

import info.pionas.rental.application.booking.BookingAccept;
import info.pionas.rental.application.booking.BookingReject;
import info.pionas.rental.application.commandregisty.CommandRegisty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/booking")
public class BookingRestController {

    private final CommandRegisty commandRegisty;

    @PutMapping("/reject/{id}")
    public void reject(@PathVariable String id) {
        commandRegisty.register(new BookingReject(id));
    }

    @PutMapping("/accept/{id}")
    public void accept(@PathVariable String id) {
        commandRegisty.register(new BookingAccept(id));
    }
}
