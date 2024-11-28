package lalalabs.pharmacy_crop.business.weather.domain.type;

import lombok.Getter;

@Getter
public enum SkyType {
    UNKNOWN(0),
    CLEAR(1),
    PARTLY_CLOUDY(2),
    MOSTLY_CLOUDY(3),
    CLOUDY(4);

    private final int code;

    SkyType(int code) {
        this.code = code;
    }

    public static SkyType fromCode(int code) {
        for (SkyType skyType : values()) {
            if (skyType.code == code) {
                return skyType;
            }
        }
        return UNKNOWN;
    }

    public static SkyType fromShortTermOverlandForecastCode(String code) {
        return switch (code) {
            case "DB01" -> CLEAR;
            case "DB02" -> PARTLY_CLOUDY;
            case "DB03" -> MOSTLY_CLOUDY;
            case "DB04" -> CLOUDY;
            default -> UNKNOWN;
        };
    }

    public static SkyType fromMediumTermForecastCode(String code) {
        return switch (code) {
            case "WB01" -> CLEAR;
            case "WB02" -> PARTLY_CLOUDY;
            case "WB03" -> MOSTLY_CLOUDY;
            case "WB04" -> CLOUDY;
            default -> UNKNOWN;
        };
    }

}
