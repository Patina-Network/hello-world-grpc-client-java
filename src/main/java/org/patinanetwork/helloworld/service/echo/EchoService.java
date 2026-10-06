package org.patinanetwork.helloworld.service.echo;

import org.patinanetwork.grpc.helloworld.v1.EchoHelloRequest;
import org.patinanetwork.grpc.helloworld.v1.GreeterServiceGrpc.GreeterServiceBlockingStub;
import org.patinanetwork.helloworld.dto.echo.EchoDto;
import org.springframework.stereotype.Service;

@Service
public class EchoService {

    private final GreeterServiceBlockingStub greeterService;

    public EchoService(final GreeterServiceBlockingStub greeterService) {
        this.greeterService = greeterService;
    }

    public EchoDto echo(final String name) {
        var response = greeterService.echoHello(
                EchoHelloRequest.newBuilder().setName(name).build());
        return EchoDto.fromEchoHelloResponse(response);
    }
}
