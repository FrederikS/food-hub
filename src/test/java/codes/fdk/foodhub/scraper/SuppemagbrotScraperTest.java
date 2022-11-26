package codes.fdk.foodhub.scraper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class SuppemagbrotScraperTest {

    @MockBean
    private HttpClient httpClient;

    @Autowired
    private SuppemagbrotScraper scraper;

    @Test
    void scrape(@Value("classpath:suppemagbrot/website.html") Resource html) {
        final CompletableFuture<HttpResponse<Object>> response = mockResponseWithBody(html);
        given(httpClient.sendAsync(any(), any())).willReturn(response);

        assertThat(scraper.scrape().join()).isNotNull().satisfies(menu -> assertSoftly(softly -> {
            softly.assertThat(menu.dishes()).hasSize(6);
            softly.assertThat(menu.dishes()).anySatisfy(dish -> {
                final String expected = "Cremige Kürbissuppe nach Art des Hauses (veget.) (glu-frei)";
                softly.assertThat(dish.name()).isEqualTo(expected);
            });
            softly.assertThat(menu.dishes()).anySatisfy(dish -> {
                final String expected = "Kichererbsenkorma mit Reis (vegan) (glu-frei)";
                softly.assertThat(dish.name()).isEqualTo(expected);
            });
            softly.assertThat(menu.dishes()).anySatisfy(dish -> {
                final String expected = "Puten-Senf-Eintopf mit Bulgur";
                softly.assertThat(dish.name()).isEqualTo(expected);
            });
            softly.assertThat(menu.dishes()).anySatisfy(dish -> {
                final String expected = "Apfel-Möhren-Ingwer-Suppe (vegan) (glu-frei)";
                softly.assertThat(dish.name()).isEqualTo(expected);
            });
            softly.assertThat(menu.dishes()).anySatisfy(dish -> {
                final String expected = "Ital. Tomaten-Tortellini-Eintopf (veget.) (wahlw. mit Parmesan)";
                softly.assertThat(dish.name()).isEqualTo(expected);
            });
            softly.assertThat(menu.dishes()).anySatisfy(dish -> {
                final String expected = "Thai-Hühnchen-Suppe mit Glasnudeln";
                softly.assertThat(dish.name()).isEqualTo(expected);
            });
        }));
    }

    @SuppressWarnings("unchecked")
    private static CompletableFuture<HttpResponse<Object>> mockResponseWithBody(Resource body) {
        final HttpResponse<Object> response = (HttpResponse<Object>) mock(HttpResponse.class);
        try (InputStream is = body.getInputStream()) {
            final String websiteHtml = StreamUtils.copyToString(is, UTF_8);
            when(response.body()).thenReturn(websiteHtml);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return completedFuture(response);
    }

}