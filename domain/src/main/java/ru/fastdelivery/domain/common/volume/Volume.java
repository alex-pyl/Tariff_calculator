package ru.fastdelivery.domain.common.volume;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@Getter
@Setter
public class Volume {

    private final Dimension length;
    private Dimension width;
    private Dimension height;
    private BigInteger value;

    public Volume(Dimension length, Dimension width, Dimension height) {
        this.length = length;
        this.width = width;
        this.height = height;
        value = length.value().multiply(width.value()).multiply(height.value());
    }

    public static BigDecimal cubicMeters(BigInteger volume) {
        return new BigDecimal(volume)
                .divide(BigDecimal.valueOf(1_000_000_000), 1000, RoundingMode.HALF_UP);
    }

    public static Volume zero() {
        return new Volume(new Dimension(BigInteger.ZERO), new Dimension(BigInteger.ZERO), new Dimension(BigInteger.ZERO));
    }

}
