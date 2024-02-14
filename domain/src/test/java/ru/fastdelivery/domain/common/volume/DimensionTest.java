package ru.fastdelivery.domain.common.volume;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DimensionTest {
    @Test
    @DisplayName("Попытка создать отрицательный габарит -> исключение")
    void whenMetersBelowZero_thenException() {
        var meters = new BigInteger("-1");
        assertThatThrownBy(() -> new Dimension(meters))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void equalsTypeWidth_same() {
        var dimension = new Dimension(new BigInteger("1000"));
        var dimensionSame = new Dimension(new BigInteger("1000"));

        assertThat(dimension)
                .isEqualTo(dimensionSame)
                .hasSameHashCodeAs(dimensionSame);
    }

    @Test
    void equalsNull_false() {
        var dimension = new Dimension(new BigInteger("4"));

        assertThat(dimension).isNotEqualTo(null);
    }

    @ParameterizedTest
    @CsvSource({ "1000, 1, -1",
            "199, 199, 0",
            "50, 999, 1" })
    void compareToTest(BigInteger low, BigInteger high, int expected) {
        var dimensionLow = new Dimension(low);
        var dimensionHigh = new Dimension(high);

        assertThat(dimensionLow.compareTo(dimensionHigh))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Первый габарит больше второго -> true")
    void whenFirstDimensionGreaterThanSecond_thenTrue() {
        var dimensionBig = new Dimension(new BigInteger("1001"));
        var dimensionSmall = new Dimension(new BigInteger("1000"));

        assertThat(dimensionBig.greaterThan(dimensionSmall)).isTrue();
    }
}
