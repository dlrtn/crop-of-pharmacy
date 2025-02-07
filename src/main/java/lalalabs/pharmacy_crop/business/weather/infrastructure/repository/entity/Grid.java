package lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grid")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Grid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nx", nullable = false)
    private Integer nx;

    @Column(name = "ny", nullable = false)
    private Integer ny;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;
}
