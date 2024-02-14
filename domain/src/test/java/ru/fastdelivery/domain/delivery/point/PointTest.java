package ru.fastdelivery.domain.delivery.point;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {
    @ParameterizedTest
    @CsvSource({"77.1539, -139.398, -77.1804, -139.55, 17166.029",
            "77.1539, 120.398, 77.1804, 129.55, 225.883",
            "77.1539, -120.398, 77.1804, 129.55, 2332.669"})
    void DistanceTest(double latitude1, double longitude1, double latitude2, double longitude2, double expected) {
        assertThat(Math.round(Point.distance(new Point(latitude1, longitude1), new Point(latitude2, longitude2))*1000)/1000.0)
                .isEqualTo(expected);
    }
}
