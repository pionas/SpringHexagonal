package info.pionas.rental.infrastructure.persistence.jpa.hotelroomoffer;

import info.pionas.rental.application.hotelroomoffer.HotelRoomOfferAssertion;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer;
import info.pionas.rental.domain.hotelroomoffer.HotelRoomOfferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static info.pionas.rental.domain.hotelroomoffer.HotelRoomOffer.Builder.hotelRoomOffer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaHotelRoomOfferRepositoryIntegrationTest {
    private static final String HOTEL_ROOM_ID = "1234";
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final LocalDate START = LocalDate.now();
    private static final LocalDate END = START.plusDays(6);

    @Autowired
    private HotelRoomOfferRepository hotelRoomOfferRepository;
    @Autowired
    private SpringJpaHotelRoomOfferTestRepository springJpaHotelRoomOfferTestRepository;

    private final List<String> hotelRoomOfferIds = new ArrayList<>();

    @AfterEach
    void deleteHotelRoomOffers() {
        springJpaHotelRoomOfferTestRepository.deleteAll(hotelRoomOfferIds);
    }

    @Test
    void shouldThrowExceptionWhenHotelRoomOfferDoesNotExist() {
        String nonExistingHotelRoomOfferId = UUID.randomUUID().toString();

        HotelRoomOfferDoesNotExistException actual = assertThrows(HotelRoomOfferDoesNotExistException.class, () -> {
            hotelRoomOfferRepository.findById(nonExistingHotelRoomOfferId);
        });

        assertThat(actual).hasMessage("Hotel room offer with id " + nonExistingHotelRoomOfferId + " does not exist");
    }

    @Test
    @Transactional
    void shouldReturnExistingHotelRoomOffer() {
        String existingId = givenExistingHotelRoomOffer(createHotelRoomOffer());

        HotelRoomOffer actual = hotelRoomOfferRepository.findById(existingId);

        HotelRoomOfferAssertion.assertThat(actual)
                .hasIdEqualTo(existingId)
                .hasHotelRoomIdEqualTo(HOTEL_ROOM_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    @Test
    @Transactional
    void shouldReturnExistingHotelRoomOfferWeWant() {
        HotelRoomOffer hotelRoomOffer1 = hotelRoomOffer()
                .withHotelRoomId(HOTEL_ROOM_ID)
                .withPrice(BigDecimal.valueOf(190))
                .withAvailability(START, END)
                .build();
        givenExistingHotelRoomOffer(hotelRoomOffer1);
        String existingId = givenExistingHotelRoomOffer(createHotelRoomOffer());
        HotelRoomOffer hotelRoom2 = hotelRoomOffer()
                .withHotelRoomId(HOTEL_ROOM_ID)
                .withPrice(BigDecimal.valueOf(190))
                .withAvailability(START, END)
                .build();
        givenExistingHotelRoomOffer(hotelRoom2);
        HotelRoomOffer hotelRoom3 = hotelRoomOffer()
                .withHotelRoomId(HOTEL_ROOM_ID)
                .withPrice(BigDecimal.valueOf(190))
                .withAvailability(START, END)
                .build();
        givenExistingHotelRoomOffer(hotelRoom3);

        HotelRoomOffer actual = hotelRoomOfferRepository.findById(existingId);

        HotelRoomOfferAssertion.assertThat(actual)
                .hasIdEqualTo(existingId)
                .hasHotelRoomIdEqualTo(HOTEL_ROOM_ID)
                .hasPriceEqualTo(PRICE)
                .hasAvailabilityEqualTo(START, END);
    }

    private String givenExistingHotelRoomOffer(HotelRoomOffer hotelRoomOffer) {
        String hotelRoomOfferId = hotelRoomOfferRepository.save(hotelRoomOffer);
        hotelRoomOfferIds.add(hotelRoomOfferId);

        return hotelRoomOfferId;
    }

    private HotelRoomOffer createHotelRoomOffer() {
        return hotelRoomOffer()
                .withHotelRoomId(HOTEL_ROOM_ID)
                .withPrice(PRICE)
                .withAvailability(START, END)
                .build();
    }
}