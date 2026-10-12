
package com.apache.camel.poc.route;

import com.apache.camel.poc.camelProcessor.UserIdProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFileRoute extends RouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(UserFileRoute.class);
    private final UserIdProcessor userIdProcessor;

    public UserFileRoute(UserIdProcessor userIdProcessor) {
        this.userIdProcessor = userIdProcessor;
    }

    @Override
    public void configure() {

        from("file:input")
            .routeId("user-file-route")
            .log("File received: ${header.CamelFileName}")
            .split(body().tokenize("\n"))
                .doTry()
                    .process(userIdProcessor)
                    .log("User ID processed: ${body}")
                .doCatch(Exception.class)
                    .process(exchange -> {
                        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        String userId = exchange.getMessage().getBody(String.class);

                        log.warn("Invalid user ID '{}': {}", userId, exception.getMessage());
                    })
                .end()
            .end();
    }
}