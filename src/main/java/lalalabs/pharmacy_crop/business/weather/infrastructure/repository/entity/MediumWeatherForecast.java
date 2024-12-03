package lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medium_weather_forecast", indexes = {
        @Index(name = "medium_weather_forecast_idx_reg_id", columnList = "reg_id"),
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MediumWeatherForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reg_id", nullable = false)
    private String regId;

    @Column(name = "tm_fc", nullable = false)
    private LocalDateTime tmFc;

    @Column(name = "tm_ef", nullable = false)
    private LocalDateTime tmEf;

    @Column(name = "sky")
    private String sky;

    @Column(name = "pre")
    private String pre;

    @Column(name = "rn_st")
    private Integer rnSt;
}

