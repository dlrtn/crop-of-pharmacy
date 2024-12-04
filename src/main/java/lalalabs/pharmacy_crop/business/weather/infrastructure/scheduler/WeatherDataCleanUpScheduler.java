package lalalabs.pharmacy_crop.business.weather.infrastructure.scheduler;

import lalalabs.pharmacy_crop.business.weather.application.WeatherDataCleanUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherDataCleanUpScheduler {

    private final WeatherDataCleanUpService weatherDataCleanUpService;


    @Scheduled(cron = "0 0 6,18 * * ?")
    public void cleanUp() {
        weatherDataCleanUpService.cleanUp();

        log.info("Weather data clean up completed");
    }
}
