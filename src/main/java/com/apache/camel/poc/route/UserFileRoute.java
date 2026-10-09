package com.apache.camel.poc.route;

import com.apache.camel.poc.processor.UserIdProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserFileRoute extends RouteBuilder {

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
        .process(userIdProcessor)
        .log("User ID inserted: ${body}");
    }
}