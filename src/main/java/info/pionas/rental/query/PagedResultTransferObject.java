package info.pionas.rental.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@Data
public class PagedResultTransferObject<T> extends RepresentationModel<PagedResultTransferObject<T>> {
    private Iterable<T> data;
    private int pageNumber;
    private int totalPages;
}
