package org.patinanetwork.helloworld.dto.greeting;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.patinanetwork.grpc.helloworld.v1.GreetingsResponse;

@Getter
@Builder
@Jacksonized
@ToString
@EqualsAndHashCode
public class GreetingsDto {

    private List<GreetingDto> replies;

    public static GreetingsDto fromGreetingsResponse(final GreetingsResponse greetings) {
        return GreetingsDto.builder()
                .replies(greetings.getRepliesList().stream()
                        .map(GreetingDto::fromGreetingResponse)
                        .toList())
                .build();
    }
}
