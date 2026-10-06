package org.patinanetwork.helloworld.dto;

import tools.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public final class Empty {

    private static final Empty INSTANCE = new Empty();

    private Empty() {}

    public static Empty of() {
        return INSTANCE;
    }
}
