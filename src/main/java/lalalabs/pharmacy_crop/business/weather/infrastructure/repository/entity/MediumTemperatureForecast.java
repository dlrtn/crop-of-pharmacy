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
@Table(name = "medium_temperature_forecast", indexes = {
        @Index(name = "medium_temperature_forecast_idx_reg_id", columnList = "reg_id") // REG_ID에 인덱스 추가
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MediumTemperatureForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AutoIncrement 기본 키
    @Column(name = "id")
    private Long id; // Auto-increment ID

    @Column(name = "reg_id", nullable = false)
    private String regId; // 지역 ID

    @Column(name = "tm_fc", nullable = false)
    private LocalDateTime tmFc; // 예보 시작 시간

    @Column(name = "tm_ef", nullable = false)
    private LocalDateTime tmEf; // 예보 종료 시간

    @Column(name = "min")
    private Float min; // 최소값

    @Column(name = "max")
    private Float max; // 최대값
}
