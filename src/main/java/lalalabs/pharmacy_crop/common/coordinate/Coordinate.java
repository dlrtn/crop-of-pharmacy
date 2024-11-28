package lalalabs.pharmacy_crop.common.coordinate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "좌표")
@Getter
@Setter
@AllArgsConstructor
public class Coordinate {
    @Schema(description = "위도")
    private double latitude;

    @Schema(description = "경도")
    private double longitude;

    public double calculateDistanceWith(Coordinate c) {
        return Math.sqrt(Math.pow(this.latitude - c.latitude, 2) + Math.pow(this.longitude - c.longitude, 2));
    }
}

