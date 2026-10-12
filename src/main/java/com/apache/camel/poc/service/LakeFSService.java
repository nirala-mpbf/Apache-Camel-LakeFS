
package com.apache.camel.poc.service;


import io.lakefs.clients.sdk.*;
import io.lakefs.clients.sdk.model.*;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class LakeFSService {

    private final RepositoriesApi repositoriesApi;
    private final ObjectsApi objectsApi;
    private final CommitsApi commitsApi;
    private final BranchesApi branchesApi;
    private final RefsApi refsApi;

    public LakeFSService(ApiClient apiClient) {
        this.repositoriesApi = new RepositoriesApi(apiClient);
        this.objectsApi = new ObjectsApi(apiClient);
        this.commitsApi = new CommitsApi(apiClient);
        this.branchesApi = new BranchesApi(apiClient);
        this.refsApi = new RefsApi(apiClient);
    }

    public RepositoryList listRepositories() throws ApiException {
        return repositoriesApi.listRepositories().execute();
    }

    public ObjectStats uploadFile(String repository, String branch, String objectPath, File file) throws ApiException {

        return objectsApi
                .uploadObject(repository, branch, objectPath)
                .content(file)
                .execute();
    }


    public Commit commitChanges(String repository, String branch, String message) throws ApiException {

        CommitCreation commitCreation = new CommitCreation();
        commitCreation.setMessage(message);

        return commitsApi
                .commit(repository, branch, commitCreation)
                .execute();
    }

    public void revertCommit(String repository, String branch, String commitId) throws ApiException {

        RevertCreation revertCreation = new RevertCreation();
        revertCreation.setRef(commitId);
        revertCreation.setParentNumber(1);

        branchesApi
                .revertBranch(repository, branch, revertCreation)
                .execute();

    }

    public Object listBranches(String repository) throws ApiException {
        return branchesApi
                .listBranches(repository)
                .execute();
    }

    public Object listCommits(String repository, String branch) throws ApiException {
        return refsApi.
                logCommits(repository, branch)
                .execute();
    }

    public Object listObjects(String repository, String branch) throws ApiException {
        return objectsApi.
                listObjects(repository, branch)
                .execute();
    }



}
