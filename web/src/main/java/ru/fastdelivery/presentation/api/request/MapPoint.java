package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record MapPoint(
        @Schema(description = "Широта, градусы", example = "55.446008")
        double latitude,
        @Schema(description = "Долгота, градусы", example = "65.339151")
        double longitude
) {
}
