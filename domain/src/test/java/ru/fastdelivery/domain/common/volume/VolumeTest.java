package ru.fastdelivery.domain.common.volume;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class VolumeTest {
    @Test
    @DisplayName("Запрос объема -> получено корректное значение")
    void whenGetVolume_thenReceiveVolume() {
        var dimension = new Dimension(new BigInteger("4"));
        var volume = new Volume(dimension, dimension, dimension);

        var actual = volume.getValue();

        assertThat(actual).isEqualByComparingTo(new BigInteger("50").pow(3));
    }

    @Test
    @DisplayName("Запрос количество куб м -> получено корректное значение")
    void whenGetKilograms_thenReceiveKg() {
        var dimension = new Dimension(new BigInteger("900"));
        var volume = new Volume(dimension, dimension, dimension);

        var actual = Volume.cubicMeters(volume.getValue());

        assertThat(actual).isEqualByComparingTo(new BigDecimal("0.729"));
    }
}
