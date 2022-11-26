package codes.fdk.foodhub.scraper;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.List;
import java.util.regex.Pattern;

import static codes.fdk.foodhub.scraper.FoodMenu.SuppemagbrotFoodMenu;

@Component
class SuppemagbrotScraper extends HtmlFoodScraper {

    private static final Pattern HEADLINE_PATTERN = Pattern.compile("Tagesmenü für den (?<date>\\d{2}\\.[aA-z]+)");
    private final ScrapingConfig config;

    public SuppemagbrotScraper(HttpClient httpClient, ScrapingConfig config) {
        super(httpClient);
        this.config = config;
    }

    @Override
    URI getWebsiteUrl() {
        return config.suppemagbrot().website();
    }

    @Override
    FoodMenu extractFood(Document document) {
        return new SuppemagbrotFoodMenu(List.of());
    }

}
