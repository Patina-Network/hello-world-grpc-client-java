package org.patinanetwork.helloworld.controller.greetings;

import org.patinanetwork.helloworld.controller.greetings.body.SayGreetingBody;
import org.patinanetwork.helloworld.dto.Empty;
import org.patinanetwork.helloworld.dto.greeting.GreetingsDto;
import org.patinanetwork.helloworld.service.greetings.GreetingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greetings")
public class GreetingsController {

    private final GreetingsService greetingsService;

    public GreetingsController(final GreetingsService greetingsService) {
        this.greetingsService = greetingsService;
    }

    @PostMapping
    public ResponseEntity<Empty> sayGreeting(@RequestBody final SayGreetingBody body) {
        greetingsService.sayGreeting(body.getSenderName(), body.getRecipientName(), body.getGreeting());
        return ResponseEntity.ok(Empty.of());
    }

    @GetMapping
    public ResponseEntity<GreetingsDto> getGreetings(@RequestParam(required = false) final String recipientName) {
        return ResponseEntity.ok(greetingsService.getGreetings(recipientName));
    }
}
