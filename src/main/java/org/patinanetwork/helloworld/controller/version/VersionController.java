package org.patinanetwork.helloworld.controller.version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private final String version;

    public VersionController(@Value("${VERSION:N/A}") final String version) {
        this.version = version;
    }

    @GetMapping(path = "/version", produces = MediaType.TEXT_PLAIN_VALUE)
    public String version() {
        return version;
    }
}
