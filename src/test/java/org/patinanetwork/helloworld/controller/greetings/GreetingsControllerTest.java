package org.patinanetwork.helloworld.controller.greetings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.patinanetwork.helloworld.controller.greetings.body.SayGreetingBody;
import org.patinanetwork.helloworld.dto.Empty;
import org.patinanetwork.helloworld.dto.greeting.GreetingDto;
import org.patinanetwork.helloworld.dto.greeting.GreetingsDto;
import org.patinanetwork.helloworld.service.greetings.GreetingsService;
import org.springframework.http.HttpStatus;

public class GreetingsControllerTest {

    private final GreetingsService greetingsService = mock(GreetingsService.class);
    private final GreetingsController greetingsController = new GreetingsController(greetingsService);

    @Test
    void sayGreetingSendsBodyAndReturnsEmpty() {
        var body = SayGreetingBody.builder()
                .senderName("Ada")
                .recipientName("Lin")
                .greeting("hi")
                .build();

        var response = greetingsController.sayGreeting(body);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(Empty.of(), response.getBody());
        verify(greetingsService).sayGreeting("Ada", "Lin", "hi");
    }

    @Test
    void getGreetingsReturnsServiceResult() {
        var greetings = GreetingsDto.builder()
                .replies(List.of(GreetingDto.builder().id(1).message("hi").build()))
                .build();
        when(greetingsService.getGreetings("Lin")).thenReturn(greetings);

        var response = greetingsController.getGreetings("Lin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(greetings, response.getBody());
    }
}
