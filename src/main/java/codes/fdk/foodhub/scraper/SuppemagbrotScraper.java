package codes.fdk.foodhub.scraper;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static codes.fdk.foodhub.scraper.FoodMenu.Dish;
import static codes.fdk.foodhub.scraper.FoodMenu.SuppemagbrotFoodMenu;

@Component
class SuppemagbrotScraper extends HtmlFoodScraper {

    private static final Logger log = LoggerFactory.getLogger(SuppemagbrotScraper.class);

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
        return new SuppemagbrotFoodMenu(extractDishes(document));
    }

    private Collection<Dish> extractDishes(Document document) {
        final String menuText = document.select(Constants.MENU_CSS_SELECTOR).text();

        return Arrays.stream(menuText.split(Constants.DIVIDING_SUB_HEADER))
                     .map(Constants.DISH_PATTERN::matcher)
                     .flatMap(Matcher::results)
                     .peek(match -> log.info("Found dish: {}", match.group(0)))
                     .map(match -> match.group(4).trim())
                     .map(name -> name.replaceAll("- ", "-"))
                     .map(Dish::new)
                     .toList();
    }

    private static final class Constants {
        private static final String MENU_CSS_SELECTOR = "#content #text-2 .textwidget > p:nth-child(3)";
        private static final String DIVIDING_SUB_HEADER = "Im Laufe des Tages:";
        private static final Pattern DISH_PATTERN = Pattern.compile(
                "((?<number>[1-6]\\.) (?<type>SUPPE|EINTOPF|SPEZIAL): (?<name>\\D+)(?![1-6]\\.))"
        );
    }

}
