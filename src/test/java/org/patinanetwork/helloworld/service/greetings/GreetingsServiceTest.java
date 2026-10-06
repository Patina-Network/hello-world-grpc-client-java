package org.patinanetwork.helloworld.service.greetings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.patinanetwork.grpc.helloworld.v1.GetGreetingsByNameRequest;
import org.patinanetwork.grpc.helloworld.v1.GreeterServiceGrpc.GreeterServiceBlockingStub;
import org.patinanetwork.grpc.helloworld.v1.GreetingResponse;
import org.patinanetwork.grpc.helloworld.v1.GreetingsResponse;
import org.patinanetwork.grpc.helloworld.v1.SayGreetingRequest;

public class GreetingsServiceTest {

    private final GreeterServiceBlockingStub greeter = mock(GreeterServiceBlockingStub.class);
    private final GreetingsService greetingsService = new GreetingsService(greeter);

    @Test
    void sayGreetingSendsAllFields() {
        greetingsService.sayGreeting("Ada", "Lin", "hi");

        verify(greeter)
                .sayGreeting(SayGreetingRequest.newBuilder()
                        .setSenderName("Ada")
                        .setRecipientName("Lin")
                        .setGreeting("hi")
                        .build());
    }

    @Test
    void sayGreetingSendsMissingFieldsAsEmpty() {
        greetingsService.sayGreeting(null, null, null);

        verify(greeter).sayGreeting(SayGreetingRequest.getDefaultInstance());
    }

    @Test
    void getGreetingsOmitsFilterWhenRecipientIsNull() {
        when(greeter.getGreetingsByName(any())).thenReturn(GreetingsResponse.getDefaultInstance());

        greetingsService.getGreetings(null);

        var request = ArgumentCaptor.forClass(GetGreetingsByNameRequest.class);
        verify(greeter).getGreetingsByName(request.capture());
        assertFalse(request.getValue().hasRecipientName());
    }

    @Test
    void getGreetingsSendsEmptyFilterWhenRecipientIsEmpty() {
        when(greeter.getGreetingsByName(any())).thenReturn(GreetingsResponse.getDefaultInstance());

        greetingsService.getGreetings("");

        var request = ArgumentCaptor.forClass(GetGreetingsByNameRequest.class);
        verify(greeter).getGreetingsByName(request.capture());
        assertTrue(request.getValue().hasRecipientName());
        assertEquals("", request.getValue().getRecipientName());
    }

    @Test
    void getGreetingsMapsReplies() {
        when(greeter.getGreetingsByName(any()))
                .thenReturn(GreetingsResponse.newBuilder()
                        .addReplies(GreetingResponse.newBuilder()
                                .setId(-1)
                                .setMessage("hi")
                                .setSenderName("Ada")
                                .setRecipientName("Lin")
                                .setReceivedAt(
                                        Timestamp.newBuilder().setSeconds(60).setNanos(5)))
                        .addReplies(GreetingResponse.newBuilder().setId(2))
                        .build());

        var greetings = greetingsService.getGreetings("Lin").getReplies();

        assertEquals(2, greetings.size());
        var first = greetings.get(0);
        assertEquals(4294967295L, first.getId());
        assertEquals("hi", first.getMessage());
        assertEquals("Ada", first.getSenderName());
        assertEquals("Lin", first.getRecipientName());
        assertEquals(Instant.ofEpochSecond(60, 5), first.getReceivedAt());
        assertNull(greetings.get(1).getReceivedAt());
    }
}
