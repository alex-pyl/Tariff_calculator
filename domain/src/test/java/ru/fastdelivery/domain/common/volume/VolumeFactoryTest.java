package ru.fastdelivery.domain.common.volume;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VolumeFactoryTest {
    @ParameterizedTest(name = "Миллиметры, кратные 50 = {arguments} -> объект создан")
    @ValueSource(longs = { 0, 100, 10_050 })
    void whenMeters50GreaterThanZero_thenObjectCreated(long amount) {
        var dimension = new Dimension(BigInteger.valueOf(amount));
        var volume = new Volume(dimension, dimension, dimension);

        assertNotNull(volume);
        assertThat(volume.getValue()).isEqualByComparingTo(BigInteger.valueOf(amount).pow(3));
    }

    @Test
    @DisplayName("Миллиметры, некратные 50 -> объект создан")
    void whenMetersGreaterThanZero_thenObjectCreated() {
        var dimension = new Dimension(BigInteger.valueOf(111));
        var volume = new Volume(dimension, dimension, dimension);

        assertNotNull(volume);
        assertThat(volume.getValue()).isEqualByComparingTo(BigInteger.valueOf(150).pow(3));
    }
}
