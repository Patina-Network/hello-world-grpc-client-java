package org.patinanetwork.helloworld.dto.greeting;

import java.time.Instant;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.patinanetwork.grpc.helloworld.v1.GreetingResponse;

@Getter
@Builder
@Jacksonized
@ToString
@EqualsAndHashCode
public class GreetingDto {

    private long id;
    private String message;
    private String senderName;
    private String recipientName;
    private Instant receivedAt;

    public static GreetingDto fromGreetingResponse(final GreetingResponse greeting) {
        var receivedAt = greeting.getReceivedAt();
        return GreetingDto.builder()
                .id(Integer.toUnsignedLong(greeting.getId()))
                .message(greeting.getMessage())
                .senderName(greeting.getSenderName())
                .recipientName(greeting.getRecipientName())
                .receivedAt(
                        greeting.hasReceivedAt()
                                ? Instant.ofEpochSecond(receivedAt.getSeconds(), receivedAt.getNanos())
                                : null)
                .build();
    }
}
