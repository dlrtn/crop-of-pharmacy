package lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity;

import jakarta.persistence.*;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "short_term_forecast", indexes = {
        @Index(name = "short_term_forecast_idx_nx_ny", columnList = "nx, ny"),
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortTermForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_date", nullable = false)
    private String baseDate;

    @Column(name = "base_time", nullable = false)
    private String baseTime;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "fcst_date", nullable = false)
    private String fcstDate;

    @Column(name = "fcst_time", nullable = false)
    private String fcstTime;

    @Column(name = "fcst_value", nullable = false)
    private String fcstValue;

    @Column(name = "nx", nullable = false)
    private Integer nx;

    @Column(name = "ny", nullable = false)
    private Integer ny;

    public static ShortTermForecast of(ShortTermWeatherApiResponse.ShortTermForecastItem item) {
        return ShortTermForecast.builder()
                .baseDate(item.baseDate())
                .baseTime(item.baseTime())
                .category(item.category())
                .fcstDate(item.fcstDate())
                .fcstTime(item.fcstTime())
                .fcstValue(item.fcstValue())
                .nx(item.nx())
                .ny(item.ny())
                .build();
    }
}
