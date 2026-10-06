package org.patinanetwork.helloworld.controller.urls;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class UrlsControllerTest {

    @Test
    void urlsDropsBlankEntriesAndTrims() {
        var controller = new UrlsController(List.of(" https://a.example ", "", "  ", "https://b.example"));

        var response = controller.urls();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of("https://a.example", "https://b.example"), response.getBody());
    }

    @Test
    void urlsIsEmptyWhenUnset() {
        assertEquals(List.of(), new UrlsController(List.of()).urls().getBody());
    }
}
