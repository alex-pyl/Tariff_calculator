package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(
        @Schema(description = "Список упаковок отправления",
                example = """
                        [{"weight": 4564,
                              "length": 345,
                              "width": 589,
                              "height": 234
                            }]""")
        @NotNull
        @NotEmpty
        List<CargoPackage> packages,

        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull
        String currencyCode,

        @Schema(description = "Пункт выдачи",
                example = "{\"latitude\" : 73.398660,\n" +
                      "     \"longitude\" : 55.027532}")
        @NotNull
        MapPoint destination,

        @Schema(description = "Пункт отправки",
                example = "{\"latitude\" : 73.398660,\n" +
                      "     \"longitude\" : 55.027532}")
        @NotNull
        MapPoint departure
) {
}
