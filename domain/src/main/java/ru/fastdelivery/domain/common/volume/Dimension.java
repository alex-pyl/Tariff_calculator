package ru.fastdelivery.domain.common.volume;

import java.math.BigInteger;

public record Dimension(BigInteger value)  implements Comparable<Dimension> {

    public static final BigInteger NORM = BigInteger.valueOf(50);

    public Dimension {
        if (isLessThanZero(value)) {
            throw new IllegalArgumentException("Dimension cannot be below Zero!");
        }
        value = normalizedValue(value);
    }
    private boolean isLessThanZero(BigInteger toCompare) {
        return BigInteger.ZERO.compareTo(toCompare) > 0;
    }
    public boolean greaterThan(Dimension dimension) {
        return value.compareTo(dimension.value()) > 0;
    }
    private BigInteger normalizedValue(BigInteger toNormalize) {
        BigInteger[] result = toNormalize.divideAndRemainder(NORM);
        return NORM.multiply(result[0].add(BigInteger.ZERO.compareTo(result[1]) != 0 ? BigInteger.ONE : BigInteger.ZERO));
    }

    @Override
    public int compareTo(Dimension o) {
        return o.value().compareTo(value());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Dimension dimension = (Dimension) obj;
        return value.compareTo(dimension.value) == 0;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
