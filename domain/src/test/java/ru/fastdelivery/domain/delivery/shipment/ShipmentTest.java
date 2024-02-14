package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.volume.Dimension;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.point.PointFactory;
import ru.fastdelivery.domain.delivery.point.PointPropertyProvider;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShipmentTest {

    final PointPropertyProvider pointPropertyProvider = mock(PointPropertyProvider.class);

    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        double exampleCoordinate = 50;
        when(pointPropertyProvider.isValidLatitude(exampleCoordinate)).thenReturn(true);
        when(pointPropertyProvider.isValidLongitude(exampleCoordinate)).thenReturn(true);
        when(pointPropertyProvider.minimalDistance()).thenReturn(exampleCoordinate);

        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);

        var packages = List.of(new Pack(weight1, Volume.zero()), new Pack(weight2, Volume.zero()));
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"),
                new PointFactory(pointPropertyProvider).create("", exampleCoordinate, exampleCoordinate),
                new PointFactory(pointPropertyProvider).create("", exampleCoordinate, exampleCoordinate));

        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }
    @Test
    void whenSummarizingVolumeOfAllPackages_thenReturnSum() {
        double exampleCoordinate = 50;
        when(pointPropertyProvider.isValidLatitude(exampleCoordinate)).thenReturn(true);
        when(pointPropertyProvider.isValidLongitude(exampleCoordinate)).thenReturn(true);
        when(pointPropertyProvider.minimalDistance()).thenReturn(exampleCoordinate);

        var volume1 = new Volume(new Dimension(BigInteger.valueOf(1000)), new Dimension(BigInteger.valueOf(1200)), new Dimension(BigInteger.valueOf(1500)));
        var volume2 = new Volume(new Dimension(BigInteger.valueOf(100)), new Dimension(BigInteger.valueOf(200)), new Dimension(BigInteger.valueOf(500)));

        var packages = List.of(new Pack(Weight.zero(), volume1), new Pack(Weight.zero(), volume2));
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"),
                new PointFactory(pointPropertyProvider).create("", exampleCoordinate, exampleCoordinate),
                new PointFactory(pointPropertyProvider).create("", exampleCoordinate, exampleCoordinate));

        var valueOfShipment = shipment.volumeAllPackages();

        assertThat(valueOfShipment).isEqualByComparingTo(BigInteger.valueOf(1_810_000_000));
    }
}