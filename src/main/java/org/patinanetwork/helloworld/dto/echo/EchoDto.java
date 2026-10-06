package org.patinanetwork.helloworld.dto.echo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.patinanetwork.grpc.helloworld.v1.EchoHelloResponse;

@Getter
@Builder
@Jacksonized
@ToString
@EqualsAndHashCode
public class EchoDto {

    private String response;

    public static EchoDto fromEchoHelloResponse(final EchoHelloResponse echo) {
        return EchoDto.builder().response(echo.getResponse()).build();
    }
}
