package ru.fastdelivery.domain.delivery.point;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PointFactory {
    private final PointPropertyProvider propertiesProvider;

    public Point create(String label, double latitude, double longitude) {
        if (!propertiesProvider.isValidLatitude(latitude)) {
            throw new IllegalArgumentException(label + " latitude value not valid");
        }
        if (!propertiesProvider.isValidLongitude(longitude)) {
            throw new IllegalArgumentException(label + " longitude value not valid");
        }

        return new Point(latitude, longitude);
    }
}
