package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.delivery.point.Point;
import ru.fastdelivery.domain.delivery.point.PointPropertyProvider;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;
    private final PointPropertyProvider pointProvider;

    public Price calc(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var volumeAllPackagesCbm = Volume.cubicMeters(shipment.volumeAllPackages());
        var weightPrice = weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg);
        var volumePrice = volumePriceProvider
                .costPerCbm()
                .multiply(volumeAllPackagesCbm);
        var minimalPrice = weightPriceProvider.minimalPrice();
        var basePrice = weightPrice.max(volumePrice).max(minimalPrice);
        var distance = Point.distance(shipment.departure(), shipment.destination());
        var minimalDistance = pointProvider.minimalDistance();
        return basePrice.multiply(BigDecimal.valueOf(Math.max(distance, minimalDistance) / minimalDistance));
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
