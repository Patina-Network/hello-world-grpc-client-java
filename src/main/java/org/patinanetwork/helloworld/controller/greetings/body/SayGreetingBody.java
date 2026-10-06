package org.patinanetwork.helloworld.controller.greetings.body;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@ToString
public class SayGreetingBody {

    private String senderName;
    private String recipientName;
    private String greeting;
}
