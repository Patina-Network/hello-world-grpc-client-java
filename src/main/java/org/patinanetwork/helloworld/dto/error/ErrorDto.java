package org.patinanetwork.helloworld.dto.error;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@ToString
@EqualsAndHashCode
public class ErrorDto {

    private String error;

    public static ErrorDto of(final String error) {
        return ErrorDto.builder().error(error).build();
    }
}
