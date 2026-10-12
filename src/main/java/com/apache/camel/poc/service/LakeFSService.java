
package com.apache.camel.poc.service;

import io.lakefs.clients.sdk.ApiClient;
import io.lakefs.clients.sdk.ApiException;
import io.lakefs.clients.sdk.ObjectsApi;
import io.lakefs.clients.sdk.CommitsApi;
import io.lakefs.clients.sdk.RepositoriesApi;
import io.lakefs.clients.sdk.model.ObjectStats;
import io.lakefs.clients.sdk.model.RepositoryList;
import io.lakefs.clients.sdk.model.Commit;
import io.lakefs.clients.sdk.model.CommitCreation;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class LakeFSService {

    private final RepositoriesApi repositoriesApi;
    private final ObjectsApi objectsApi;
    private final CommitsApi commitsApi;

    public LakeFSService(ApiClient apiClient) {
        this.repositoriesApi = new RepositoriesApi(apiClient);
        this.objectsApi = new ObjectsApi(apiClient);
        this.commitsApi = new CommitsApi(apiClient);
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



}
