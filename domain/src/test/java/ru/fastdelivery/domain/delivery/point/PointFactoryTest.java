package ru.fastdelivery.domain.delivery.point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PointFactoryTest {

    PointPropertyProvider mockProvider = mock(PointPropertyProvider.class);
    PointFactory factory = new PointFactory(mockProvider);

    @Test
    @DisplayName("Неправильное значение широты -> исключение")
    void whenLongitudeInvalid_thenThrowException() {
        double testValue = 1;
        when(mockProvider.isValidLongitude(testValue)).thenReturn(false);
        when(mockProvider.isValidLatitude(testValue)).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> factory.create("", testValue, testValue));
    }

    @Test
    @DisplayName("Неправильное значение долготы -> исключение")
    void whenLatitudeInvalid_thenThrowException() {
        double testValue = 1;
        when(mockProvider.isValidLongitude(testValue)).thenReturn(true);
        when(mockProvider.isValidLatitude(testValue)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> factory.create("", testValue, testValue));
    }

    @Test
    @DisplayName("Правильные значения координат -> новый объект")
    void whenCodeIsAvailable_thenObjectCreated() {
        double testValue = 1;
        when(mockProvider.isValidLongitude(testValue)).thenReturn(true);
        when(mockProvider.isValidLatitude(testValue)).thenReturn(true);

        assertThat(factory.create("", testValue, testValue)).isNotNull();
    }

}
