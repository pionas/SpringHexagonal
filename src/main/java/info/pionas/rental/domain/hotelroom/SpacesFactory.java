package info.pionas.rental.domain.hotelroom;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

class SpacesFactory {
    private SpacesFactory() {
    }

    public static List<Space> create(Map<String, Double> definition) {
        return definition.entrySet().stream()
                .map(SpacesFactory::asSpace)
                .collect(toList());
    }

    private static Space asSpace(Map.Entry<String, Double> entry) {
        SquareMeter squareMeter = new SquareMeter(entry.getValue());

        return new Space(entry.getKey(), squareMeter);
    }
}
