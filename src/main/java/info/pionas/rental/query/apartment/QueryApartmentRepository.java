package info.pionas.rental.query.apartment;

import static java.util.Collections.emptyList;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Adi
 */
@RequiredArgsConstructor
public class QueryApartmentRepository {

    private final SpringQueryApartmentRepository springQueryApartmentRepository;
    private final SpringQueryApartmentBookingHistoryRepository springQueryApartmentBookingHistoryRepository;

    public Iterable<ApartmentReadModel> findAll() {
//        return springQueryApartmentRepository.findAll();
        return emptyList();
    }

    public ApartmentDetails findById(String id) {
//        ApartmentReadModel apartmentReadModel = springQueryApartmentRepository.findById(id).get();
//        ApartmentBookingHistoryReadModel apartmentBookingHistoryReadModel = springQueryApartmentBookingHistoryRepository.findById(id).get();
//        return new ApartmentDetails(apartmentReadModel, apartmentBookingHistoryReadModel);
        return null;
    }
}
