package org.patinanetwork.helloworld.service.echo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.patinanetwork.grpc.helloworld.v1.EchoHelloRequest;
import org.patinanetwork.grpc.helloworld.v1.EchoHelloResponse;
import org.patinanetwork.grpc.helloworld.v1.GreeterServiceGrpc.GreeterServiceBlockingStub;

public class EchoServiceTest {

    private final GreeterServiceBlockingStub greeterService = mock(GreeterServiceBlockingStub.class);
    private final EchoService echoService = new EchoService(greeterService);

    @Test
    void echoSendsNameAndReturnsResponse() {
        when(greeterService.echoHello(any()))
                .thenReturn(EchoHelloResponse.newBuilder()
                        .setResponse("server says hello, Ada!")
                        .build());

        var echo = echoService.echo("Ada");

        assertEquals("server says hello, Ada!", echo.getResponse());
        verify(greeterService)
                .echoHello(EchoHelloRequest.newBuilder().setName("Ada").build());
    }
}
