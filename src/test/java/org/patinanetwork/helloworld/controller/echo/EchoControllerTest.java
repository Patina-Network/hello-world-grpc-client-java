package org.patinanetwork.helloworld.controller.echo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.patinanetwork.helloworld.dto.echo.EchoDto;
import org.patinanetwork.helloworld.service.echo.EchoService;
import org.springframework.http.HttpStatus;

public class EchoControllerTest {

    private final EchoService echoService = mock(EchoService.class);
    private final EchoController echoController = new EchoController(echoService);

    @Test
    void echoReturnsServiceResult() {
        var echo = EchoDto.builder().response("server says hello, Ada!").build();
        when(echoService.echo("Ada")).thenReturn(echo);

        var response = echoController.echo("Ada");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(echo, response.getBody());
    }
}
