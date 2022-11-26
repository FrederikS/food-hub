package codes.fdk.foodhub;

import codes.fdk.foodhub.scraper.ScrapingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ScrapingConfig.class)
public class FoodHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodHubApplication.class, args);
    }

}
