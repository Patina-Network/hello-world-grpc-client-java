package org.patinanetwork.helloworld.controller.urls;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlsController {

    private final List<String> urls;

    public UrlsController(@Value("${HELLO_WORLD_CLIENT_URLS:}") final List<String> urls) {
        this.urls =
                urls.stream().map(String::trim).filter(url -> !url.isEmpty()).toList();
    }

    @GetMapping("/urls")
    public ResponseEntity<List<String>> urls() {
        return ResponseEntity.ok(urls);
    }
}
