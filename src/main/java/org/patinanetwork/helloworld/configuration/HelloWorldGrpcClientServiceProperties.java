package org.patinanetwork.helloworld.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("grpc.hello-world-client-service")
public record HelloWorldGrpcClientServiceProperties(String host, boolean tls, long timeoutMs) {}
