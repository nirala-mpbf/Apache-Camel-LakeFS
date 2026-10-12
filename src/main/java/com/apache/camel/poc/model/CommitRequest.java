package com.apache.camel.poc.model;

public record CommitRequest(
        String repository,
        String branch,
        String message
) {}
