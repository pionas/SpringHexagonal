package info.pionas.rental.infrastructure.persistence.jpa.owner;

import info.pionas.rental.domain.owner.OwnerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaOwnerRepository implements OwnerRepository {
    @Override
    public boolean exists(String ownerId) {
        return true;
    }
}
