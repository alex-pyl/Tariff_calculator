package ru.fastdelivery.domain.delivery.point;

public interface PointPropertyProvider {
    boolean isValidLatitude(double value);
    boolean isValidLongitude(double value);
    double minimalDistance();
}
