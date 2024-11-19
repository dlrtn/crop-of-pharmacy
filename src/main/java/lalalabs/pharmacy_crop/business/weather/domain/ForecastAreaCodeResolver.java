package lalalabs.pharmacy_crop.business.weather.domain;

import java.util.List;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "forecast")
public class ForecastAreaCodeResolver {

    private List<ForecastAreaCode> areaCodes;

    public ForecastAreaCode resolve(Coordinate coordinate) {
        double minDistance = Double.MAX_VALUE;
        ForecastAreaCode minAreaCode = null;

        for (ForecastAreaCode areaCode : areaCodes) {
            double distance = coordinate.calculateDistanceWith(areaCode.getCoordinate());

            if (distance < minDistance) {
                minDistance = distance;
                minAreaCode = areaCode;
            }
        }

        return minAreaCode;
    }
}
