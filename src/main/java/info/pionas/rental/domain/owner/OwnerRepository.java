package info.pionas.rental.domain.owner;

public interface OwnerRepository {
    boolean exists(String ownerId);
}
