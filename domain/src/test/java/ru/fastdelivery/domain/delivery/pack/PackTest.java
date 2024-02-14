package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.volume.Dimension;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        assertThatThrownBy(() -> new Pack(weight, Volume.zero()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenWeightLessThanMaxWeight_thenObjectCreated() {
        var actual = new Pack(new Weight(BigInteger.valueOf(1_000)), Volume.zero());
        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }

    @Test
    void whenDimensionMoreThanMaxWeight_thenThrowException() {
        var dimension = new Dimension(BigInteger.valueOf(1_501));
        assertThatThrownBy(() -> new Pack(Weight.zero(), new Volume(dimension, dimension, dimension)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenDimensionLessThanMaxWeight_thenObjectCreated() {
        var dimension = new Dimension(BigInteger.valueOf(1_000));
        var actual = new Pack(Weight.zero(), new Volume(dimension, dimension, dimension));
        assertThat(actual.volume().getHeight()).isEqualTo(new Dimension(BigInteger.valueOf(1_000)));
    }
}