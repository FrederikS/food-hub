package codes.fdk.foodhub.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public abstract class HtmlFoodScraper implements FoodScraper {

    private final HttpClient httpClient;

    protected HtmlFoodScraper(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    abstract URI getWebsiteUrl();

    abstract FoodMenu extractFood(Document document);

    @Override
    public CompletableFuture<FoodMenu> scrape() {
        final HttpRequest request = HttpRequest.newBuilder().GET().uri(getWebsiteUrl()).build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                         .thenApply(HttpResponse::body)
                         .thenApply(Jsoup::parse)
                         .thenApply(this::extractFood);
    }

}
