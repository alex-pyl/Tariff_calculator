package ru.fastdelivery.domain.common.volume;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DimensionFactoryTest {
    @ParameterizedTest(name = "Миллиметры, кратные 50 = {arguments} -> объект создан")
    @ValueSource(longs = { 0, 100, 10_050 })
    void whenMeters50GreaterThanZero_thenObjectCreated(long amount) {
        var dimension = new Dimension(BigInteger.valueOf(amount));

        assertNotNull(dimension);
        assertThat(dimension.value()).isEqualByComparingTo(BigInteger.valueOf(amount));
    }

    @ParameterizedTest(name = "Миллиметры, некратные 50 = {arguments} -> объект создан")
    @CsvSource({ "11, 50",
            "222, 250",
            "6666, 6700"})
    void whenMetersGreaterThanZero_thenObjectCreated(long amount, long expected) {
        var dimension = new Dimension(BigInteger.valueOf(amount));

        assertNotNull(dimension);
        assertThat(dimension.value()).isEqualByComparingTo(BigInteger.valueOf(expected));
    }

    @ParameterizedTest(name = "Миллиметры = {arguments} -> исключение")
    @ValueSource(longs = { -1, -100, -10_000 })
    @DisplayName("Значение габарита ниже 0.00 -> исключение")
    void whenMetersLessThanZero_thenThrowException(long amount) {
        assertThatThrownBy(() -> new Dimension(BigInteger.valueOf(amount)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
