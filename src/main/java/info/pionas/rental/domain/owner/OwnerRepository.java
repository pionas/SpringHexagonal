package info.pionas.rental.domain.owner;

import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository {
    boolean exists(String ownerId);
}
