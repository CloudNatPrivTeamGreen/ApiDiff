package com.teamgreen.apidiff.model;

import io.swagger.v3.oas.models.PathItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChangedOperation {
    private String pathUrl;
    private PathItem.HttpMethod method;
    private List<String> changedFields;



    public ChangedOperation(org.openapitools.openapidiff.core.model.ChangedOperation changedOperation) {
        this.pathUrl = changedOperation.getPathUrl();
        this.method = changedOperation.getHttpMethod();
        this.changedFields = new ArrayList<>();
        if(changedOperation.getSummary()!=null) changedFields.add("summary");
        if(changedOperation.getDescription()!=null) changedFields.add("description");
        if(changedOperation.getOperationId()!=null) changedFields.add("operationId");
        if(changedOperation.getParameters()!=null) changedFields.add("parameters");
        if(changedOperation.getRequestBody()!=null) changedFields.add("requestBody");
        if(changedOperation.getApiResponses()!=null) changedFields.add("apiResponses");
        if(changedOperation.getSecurityRequirements()!=null) changedFields.add("securityRequirements");
        if(changedOperation.getExtensions()!=null) changedFields.add("extensions");

    }

}
