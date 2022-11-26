package codes.fdk.foodhub.scraper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
class HttpClientConfig {

    @Bean
    HttpClient httpClient() {
        return HttpClient.newBuilder().build();
    }

}
