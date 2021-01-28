package info.pionas.rental.domain.event;

public class FakeEventIdFactory implements EventIdFactory {
    public static final String UUID = "e72e74b8-52af-11eb-ae93-0242ac130002";
    @Override
    public String create() {
        return UUID;
    }
}
