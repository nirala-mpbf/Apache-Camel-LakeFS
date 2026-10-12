package com.apache.camel.poc.controller;

import com.apache.camel.poc.model.LakeFSDetails;
import com.apache.camel.poc.service.LakeFSService;
import io.lakefs.clients.sdk.ApiException;
import io.lakefs.clients.sdk.model.Commit;
import io.lakefs.clients.sdk.model.RepositoryList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lakefs")
public class LakeFSController {

    private final LakeFSService lakeFSService;

    public LakeFSController(LakeFSService lakeFSService) {
        this.lakeFSService = lakeFSService;
    }

    @GetMapping("/repositories")
    public ResponseEntity<RepositoryList> getAllRepositories() throws ApiException {
        return ResponseEntity.ok(
                lakeFSService.listRepositories()
        );
    }

    @PostMapping("/repositories/branches")
    public ResponseEntity<Object> getBranches(@RequestBody LakeFSDetails request) throws ApiException {
        return ResponseEntity.ok(
                lakeFSService.listBranches(request.getRepository())
        );
    }

    @PostMapping("/commits-history")
    public ResponseEntity<Object> getCommits(@RequestBody LakeFSDetails request) throws ApiException {
        return ResponseEntity.ok(
                lakeFSService.listCommits(request.getRepository(), request.getBranch())
        );
    }

    //commit pending commits
    @PostMapping("/commit")
    public ResponseEntity<Commit> commitChanges(@RequestBody LakeFSDetails request) throws ApiException {

        Commit commit = lakeFSService.commitChanges(request.getRepository(), request.getBranch(), request.getMessage());
        return ResponseEntity.ok(commit);
    }

    @PostMapping("/revert")
    public ResponseEntity<String> revertCommit(@RequestBody LakeFSDetails request) throws ApiException {

        lakeFSService.revertCommit(request.getRepository(), request.getBranch(), request.getCommitId());
        return ResponseEntity.ok("Reverted: " + request.getCommitId());
    }

    @PostMapping("/repositories/objects")
    public ResponseEntity<Object> getObjects(@RequestBody LakeFSDetails request) throws ApiException {

        return ResponseEntity.ok(
                lakeFSService.listObjects(request.getRepository(), request.getBranch())
        );
    }
}