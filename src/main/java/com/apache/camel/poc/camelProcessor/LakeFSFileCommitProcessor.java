package com.apache.camel.poc.camelProcessor;

import com.apache.camel.poc.service.LakeFSService;
import io.lakefs.clients.sdk.ApiException;
import io.lakefs.clients.sdk.model.Commit;
import io.lakefs.clients.sdk.model.ObjectStats;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LakeFSFileCommitProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(LakeFSFileCommitProcessor.class);
    private final LakeFSService lakeFSService;

    public LakeFSFileCommitProcessor(LakeFSService lakeFSService) {
        this.lakeFSService = lakeFSService;
    }


    @Override
    public void process(Exchange exchange) throws Exception {

        String fileName = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
        File file = exchange.getIn().getBody(File.class);

        if (file == null || !file.isFile()) {
            throw new IllegalStateException("Cannot access input file: " + fileName);
        }

        ObjectStats uploadResult = lakeFSService.uploadFile(
                "repo-user-data",
                "main",
                fileName,
                file);

        log.info("File uploaded to lakeFS: {}", uploadResult.getPath());

        try {
            Commit commit = lakeFSService.commitChanges(
                    "repo-user-data",
                    "main",
                    "Updated Data: " + fileName);

            log.info("File '{}' committed successfully. Commit ID: {}", fileName, commit.getId());

        } catch (ApiException e) {
            if (e.getCode() == 400 && e.getResponseBody() != null && e.getResponseBody().contains("commit: no changes")) {
                log.info("No new changes for '{}'; no commit was created.", fileName);
            } else {
                throw e;
            }
        }
    }

}
