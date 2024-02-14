package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.delivery.point.PointPropertyProvider;

@Configuration
@ConfigurationProperties("area")
@Setter
public class PointProperties implements PointPropertyProvider {

    private double latitudeMin;
    private double latitudeMax;
    private double longitudeMin;
    private double longitudeMax;
    private double minimal;


    @Override
    public boolean isValidLatitude(double value) {
        return latitudeMin <= value && latitudeMax >= value;
    }

    @Override
    public boolean isValidLongitude(double value) {
        return longitudeMin <= value && longitudeMax >= value;
    }

    @Override
    public double minimalDistance() {
        return minimal;
    }
}
