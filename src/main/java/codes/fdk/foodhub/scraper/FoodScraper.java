package codes.fdk.foodhub.scraper;

import java.util.concurrent.CompletableFuture;

public interface FoodScraper {
    CompletableFuture<FoodMenu> scrape();
}
