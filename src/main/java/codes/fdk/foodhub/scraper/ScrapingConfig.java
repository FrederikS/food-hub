package codes.fdk.foodhub.scraper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.time.Duration;

@Validated
@ConfigurationProperties("app.scraper")
public record ScrapingConfig(@Valid @NotNull SchedulingProperties scheduling,
                             @Valid @NotNull ScraperProperties suppemagbrot) {

    public record SchedulingProperties(boolean enabled, @NotNull Duration pollingInterval) {}
    public record ScraperProperties(@NotNull URI website) {}

}
