package com.teamgreen.apidiff.model;

import io.swagger.v3.oas.models.PathItem;
import lombok.Data;
import org.openapitools.openapidiff.core.model.Endpoint;

@Data
public class MissingEndpoint {
    private String pathUrl;
    private PathItem.HttpMethod method;
    private String summary;
    private boolean pathIsStillPresent;

    public MissingEndpoint(Endpoint endpoint) {
        this.pathUrl = endpoint.getPathUrl();
        this.method = endpoint.getMethod();
        this.summary = endpoint.getSummary();
        this.pathIsStillPresent = (endpoint.getPath()) == null; //If the path is null, this means the path still exists in the new api. Only the operation is missing.
    }
}
