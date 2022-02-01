package com.teamgreen.apidiff.model;

import lombok.Data;
import org.openapitools.openapidiff.core.model.ChangedExtensions;
import org.openapitools.openapidiff.core.model.ChangedOperation;
import org.openapitools.openapidiff.core.model.ChangedSchema;


import java.util.List;

@Data
public class ApiDiff {
    private boolean generalDifferenceGiven;
    private boolean potentiallyPrivacyRelatedDifferencesGiven;
    private List<NewEndpoint> newEndpoints;
    private List<MissingEndpoint> missingEndpoints;
    private List<ChangedOperation> changedOperations;
    private List<ChangedSchema> changedSchemas;

    public ApiDiff() {
        this.generalDifferenceGiven = false;
    }

    public ApiDiff(List<NewEndpoint> newEndpoints, List<MissingEndpoint> missingEndpoints, List<ChangedOperation> changedOperations, List<ChangedSchema> changedSchemas) {
        this.newEndpoints = newEndpoints;
        this.missingEndpoints = missingEndpoints;
        this.changedOperations = changedOperations;
        this.changedSchemas = changedSchemas;
        this.generalDifferenceGiven = true;
        this.potentiallyPrivacyRelatedDifferencesGiven = !newEndpoints.isEmpty() || !missingEndpoints.isEmpty() || !changedOperations.isEmpty() || !changedSchemas.isEmpty();
    }
}
