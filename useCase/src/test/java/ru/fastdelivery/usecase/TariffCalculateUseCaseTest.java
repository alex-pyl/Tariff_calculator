package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.volume.Dimension;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.point.PointFactory;
import ru.fastdelivery.domain.delivery.point.PointPropertyProvider;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TariffCalculateUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final VolumePriceProvider valuePriceProvider = mock(VolumePriceProvider.class);
    final PointPropertyProvider pointPropertyProvider = mock(PointPropertyProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    final TariffCalculateUseCase tariffCalculateUseCase = new TariffCalculateUseCase(weightPriceProvider, valuePriceProvider, pointPropertyProvider);

    @Test
    @DisplayName("Расчет стоимости доставки высокой плотности на минимальное расстояние -> успешно")
    void whenCalculateWeightPrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerCbm = new Price(BigDecimal.valueOf(200), currency);
        double exampleCoordinate = 50;

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(valuePriceProvider.costPerCbm()).thenReturn(pricePerCbm);
        when(pointPropertyProvider.isValidLatitude(exampleCoordinate)).thenReturn(true);
        when(pointPropertyProvider.isValidLongitude(exampleCoordinate)).thenReturn(true);
        when(pointPropertyProvider.minimalDistance()).thenReturn(exampleCoordinate);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1200)), Volume.zero())),
                new CurrencyFactory(code -> true).create("RUB"),
                new PointFactory(pointPropertyProvider).create("", exampleCoordinate, exampleCoordinate),
                new PointFactory(pointPropertyProvider).create("", exampleCoordinate, exampleCoordinate));
        var expectedPrice = new Price(BigDecimal.valueOf(120), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }
    @Test
    @DisplayName("Расчет стоимости доставки низкой плотности на минимальное расстояние -> успешно")
    void whenCalculateVolumePrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerCbm = new Price(BigDecimal.valueOf(200), currency);
        double exampleCoordinate = 50;

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(valuePriceProvider.costPerCbm()).thenReturn(pricePerCbm);
        when(pointPropertyProvider.isValidLatitude(exampleCoordinate)).thenReturn(true);
        when(pointPropertyProvider.isValidLongitude(exampleCoordinate)).thenReturn(true);
        when(pointPropertyProvider.minimalDistance()).thenReturn(exampleCoordinate);

        var shipment = new Shipment(List.of(new Pack(Weight.zero(),
                                            new Volume(new Dimension(BigInteger.valueOf(1000)), new Dimension(BigInteger.valueOf(1200)), new Dimension(BigInteger.valueOf(1500))))),
                new CurrencyFactory(code -> true).create("RUB"),
                new PointFactory(pointPropertyProvider).create("", exampleCoordinate, exampleCoordinate),
                new PointFactory(pointPropertyProvider).create("", exampleCoordinate, exampleCoordinate));
        var expectedPrice = new Price(BigDecimal.valueOf(360), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }
    @Test
    @DisplayName("Расчет стоимости доставки с учетом расстояния -> успешно")
    void whenCalculateDistancePrice_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerCbm = new Price(BigDecimal.valueOf(200), currency);
        //double exampleCoordinate = 50;

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(valuePriceProvider.costPerCbm()).thenReturn(pricePerCbm);
        when(pointPropertyProvider.isValidLatitude(anyDouble())).thenReturn(true);
        when(pointPropertyProvider.isValidLongitude(anyDouble())).thenReturn(true);
        when(pointPropertyProvider.minimalDistance()).thenReturn(450.0);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1200)), Volume.zero())),
                new CurrencyFactory(code -> true).create("RUB"),
                new PointFactory(pointPropertyProvider).create("", 50, 50),
                new PointFactory(pointPropertyProvider).create("", 60, 60));
        var expectedPrice = new Price(BigDecimal.valueOf(341.1), currency);

        var actualPrice = tariffCalculateUseCase.calc(shipment);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        BigDecimal minimalValue = BigDecimal.TEN;
        var minimalPrice = new Price(minimalValue, currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var actual = tariffCalculateUseCase.minimalPrice();

        assertThat(actual).isEqualTo(minimalPrice);
    }

}