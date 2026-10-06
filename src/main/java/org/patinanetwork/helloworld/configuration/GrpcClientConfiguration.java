package org.patinanetwork.helloworld.configuration;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.MethodDescriptor;
import java.util.concurrent.TimeUnit;
import org.patinanetwork.grpc.helloworld.v1.GreeterServiceGrpc;
import org.patinanetwork.grpc.helloworld.v1.GreeterServiceGrpc.GreeterServiceBlockingStub;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HelloWorldGrpcClientServiceProperties.class)
public class GrpcClientConfiguration {

    @Bean
    ManagedChannel helloWorldServiceChannel(final HelloWorldGrpcClientServiceProperties properties) {
        var builder = ManagedChannelBuilder.forTarget(properties.host()).intercept(new ClientInterceptor() {
            @Override
            public <Q, R> ClientCall<Q, R> interceptCall(
                    final MethodDescriptor<Q, R> method, final CallOptions options, final Channel next) {
                return next.newCall(method, options.withDeadlineAfter(properties.timeoutMs(), TimeUnit.MILLISECONDS));
            }
        });
        return (properties.tls() ? builder.useTransportSecurity() : builder.usePlaintext()).build();
    }

    @Bean
    GreeterServiceBlockingStub greeterServiceBlockingStub(final ManagedChannel helloWorldServiceChannel) {
        return GreeterServiceGrpc.newBlockingStub(helloWorldServiceChannel);
    }
}
