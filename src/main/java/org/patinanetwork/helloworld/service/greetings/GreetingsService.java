package org.patinanetwork.helloworld.service.greetings;

import java.util.Objects;
import org.patinanetwork.grpc.helloworld.v1.GetGreetingsByNameRequest;
import org.patinanetwork.grpc.helloworld.v1.GreeterServiceGrpc.GreeterServiceBlockingStub;
import org.patinanetwork.grpc.helloworld.v1.SayGreetingRequest;
import org.patinanetwork.helloworld.dto.greeting.GreetingsDto;
import org.springframework.stereotype.Service;

@Service
public class GreetingsService {

    private final GreeterServiceBlockingStub greeter;

    public GreetingsService(final GreeterServiceBlockingStub greeter) {
        this.greeter = greeter;
    }

    public void sayGreeting(final String senderName, final String recipientName, final String greeting) {
        greeter.sayGreeting(SayGreetingRequest.newBuilder()
                .setSenderName(Objects.requireNonNullElse(senderName, ""))
                .setRecipientName(Objects.requireNonNullElse(recipientName, ""))
                .setGreeting(Objects.requireNonNullElse(greeting, ""))
                .build());
    }

    public GreetingsDto getGreetings(final String recipientName) {
        var request = GetGreetingsByNameRequest.newBuilder();
        if (recipientName != null) {
            request.setRecipientName(recipientName);
        }
        return GreetingsDto.fromGreetingsResponse(greeter.getGreetingsByName(request.build()));
    }
}
