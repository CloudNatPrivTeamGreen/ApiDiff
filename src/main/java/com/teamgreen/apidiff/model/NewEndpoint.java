package com.teamgreen.apidiff.model;

import io.swagger.v3.oas.models.PathItem;
import lombok.Data;
import org.openapitools.openapidiff.core.model.Endpoint;

@Data
public class NewEndpoint {
    private String pathUrl;
    private PathItem.HttpMethod method;
    private String summary;
    private boolean pathIsNew;

    public NewEndpoint(Endpoint endpoint) {
        this.pathUrl = endpoint.getPathUrl();
        this.method = endpoint.getMethod();
        this.summary = endpoint.getSummary();
        this.pathIsNew = (endpoint.getPath()) != null; //If the path is null, this means the path was present in the old api and is not new. Only the operation is new.
    }
}
