package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.volume.Dimension;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
public record Pack(Weight weight, Volume volume) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));
    private static final Dimension maxDimension = new Dimension(BigInteger.valueOf(1_500));

    public Pack {
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        }
        if (volume.getHeight().greaterThan(maxDimension)) {
            throw new IllegalArgumentException("Package height can't be more than " + maxDimension);
        }
        if (volume.getLength().greaterThan(maxDimension)) {
            throw new IllegalArgumentException("Package length can't be more than " + maxDimension);
        }
        if (volume.getWidth().greaterThan(maxDimension)) {
            throw new IllegalArgumentException("Package width can't be more than " + maxDimension);
        }
    }
}
