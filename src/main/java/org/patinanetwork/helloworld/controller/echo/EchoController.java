package org.patinanetwork.helloworld.controller.echo;

import org.patinanetwork.helloworld.dto.echo.EchoDto;
import org.patinanetwork.helloworld.service.echo.EchoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/echo")
public class EchoController {

    private final EchoService echoService;

    public EchoController(final EchoService echoService) {
        this.echoService = echoService;
    }

    @GetMapping
    public ResponseEntity<EchoDto> echo(@RequestParam(defaultValue = "") final String name) {
        return ResponseEntity.ok(echoService.echo(name));
    }
}
