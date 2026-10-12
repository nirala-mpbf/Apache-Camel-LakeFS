package com.apache.camel.poc.lakeFSTest;

import com.apache.camel.poc.service.LakeFSService;
import io.lakefs.clients.sdk.ApiException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LakeFSTestRunner implements CommandLineRunner {

    private final LakeFSService lakeFSService;

    public LakeFSTestRunner(LakeFSService lakeFSService) {
        this.lakeFSService = lakeFSService;
    }

    @Override
    public void run(String... args) {
        File file = new File("input/users.txt");

        if (!file.isFile()) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return;
        }


        try {
            var uploadResult = lakeFSService.uploadFile("user-data-repo", "main", "input/users.txt", file);

            System.out.println("File uploaded: " + uploadResult.getPath());

            var commit = lakeFSService.commitChanges("user-data-repo", "main", "Upload users.txt");

            System.out.println("Commit successful!");
            System.out.println("Commit ID: " + commit.getId());

        } catch (ApiException e) {
            System.err.println("lakeFS operation failed: " + e.getMessage());
        }
    }
}
