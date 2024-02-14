package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.domain.delivery.point.PointFactory;
import ru.fastdelivery.domain.delivery.point.PointPropertyProvider;
import ru.fastdelivery.usecase.TariffCalculateUseCase;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public PointFactory pointFactory(PointPropertyProvider pointProperties) {
        return new PointFactory(pointProperties);
    }

    @Bean
    public TariffCalculateUseCase tariffCalculateUseCase(WeightPriceProvider weightPriceProvider, VolumePriceProvider volumePriceProvider, PointPropertyProvider pointProvider) {
        return new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider, pointProvider);
    }
}
