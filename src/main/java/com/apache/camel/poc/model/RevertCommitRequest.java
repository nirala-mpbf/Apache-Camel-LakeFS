package com.apache.camel.poc.model;

public record RevertCommitRequest(
        String repository,
        String branch,
        String commitId
) {}