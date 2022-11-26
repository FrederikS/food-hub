package codes.fdk.foodhub.scraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Component
public class ScraperScheduler {

    private static final Logger log = LoggerFactory.getLogger(ScraperScheduler.class);

    private final ScrapingConfig config;
    private final Collection<FoodScraper> scrapers;

    public ScraperScheduler(ScrapingConfig config, Collection<FoodScraper> scrapers) {
        this.config = config;
        this.scrapers = scrapers;
    }

    @Scheduled(fixedRateString = "${app.scraper.scheduling.polling-interval}")
    private void run() {
        if (config.scheduling().enabled()) {
            scrapers.stream()
                    .map(FoodScraper::scrape)
                    .map(CompletableFuture::join)
                    .forEach(menu -> log.info("Scraped menu: {}", menu));
        }
    }

}
