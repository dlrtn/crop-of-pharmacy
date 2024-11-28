package lalalabs.pharmacy_crop.business.weather.domain.type;

public enum PrecipitationType {
    NO_PRECIPITATION(0),
    RAIN(1),
    RAIN_SNOW(2),
    SNOW(3),
    SNOW_RAIN(4),
    UNKNOWN(5);

    private final int code;

    PrecipitationType(int code) {
        this.code = code;
    }

    public static PrecipitationType fromCode(int code) {
        for (PrecipitationType precipitationType : values()) {
            if (precipitationType.code == code) {
                return precipitationType;
            }
        }
        return UNKNOWN;
    }

    public static PrecipitationType fromMediumForecastCode(String code) {
        return switch (code) {
            case "WB00" -> NO_PRECIPITATION;
            case "WB09" -> RAIN;
            case "WB10" -> RAIN_SNOW;
            case "WB11" -> SNOW;
            case "WB12" -> SNOW_RAIN;
            default -> UNKNOWN;
        };
    }

}
