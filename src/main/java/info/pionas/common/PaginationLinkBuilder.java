package info.pionas.common;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
public class PaginationLinkBuilder {
    private final int pageNumber;
    private final int totalPage;
    private Link prevPage;
    private Link currentPage;
    private Link nextPage;

    public void setPrevPage(PagedResultTransferObject o) {
        if (o == null) {
            return;
        }
        this.prevPage = linkTo(o).withRel("prev");
    }

    public void setCurrentPage(PagedResultTransferObject o) {
        if (o == null) {
            return;
        }
        this.currentPage = linkTo(o).withSelfRel();
    }

    public void setNextPage(PagedResultTransferObject o) {
        if (o == null) {
            return;
        }
        this.nextPage = linkTo(o).withRel("next");
    }

    public ArrayList<Link> getLinks() {
        ArrayList<Link> links = new ArrayList<Link>();
        if (this.prevPage != null && pageNumber > 0) {
            links.add(this.prevPage);
        }
        if (this.currentPage != null) {
            links.add(this.currentPage);
        }

        if (this.nextPage != null && pageNumber < totalPage - 1) {
            links.add(this.nextPage);
        }
        return links;
    }
}
