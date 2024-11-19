package lalalabs.pharmacy_crop.common.coordinate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Coordinate {
    private double latitude;
    private double longitude;

    public double calculateDistanceWith(Coordinate c) {
        return Math.sqrt(Math.pow(this.latitude - c.latitude, 2) + Math.pow(this.longitude - c.longitude, 2));
    }
}

