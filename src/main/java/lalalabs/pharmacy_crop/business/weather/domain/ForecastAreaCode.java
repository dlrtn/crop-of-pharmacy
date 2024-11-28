package lalalabs.pharmacy_crop.business.weather.domain;

import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ForecastAreaCode {
    private String code;
    private String generalCode;
    private String name;
    private Coordinate coordinate;
}
