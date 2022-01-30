package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.openapitools.openapidiff.core.model.ChangedOperation;
import org.openapitools.openapidiff.core.model.ChangedSchema;
import org.openapitools.openapidiff.core.model.Endpoint;


import java.util.List;

@Data
@Builder
public class ApiDiff {
    private boolean generalDifferenceGiven;
    private List<Endpoint> newEndpoints;
    private List<Endpoint> missingEndpoints;
    private List<ChangedOperation> changedOperations;
    private List<ChangedSchema> changedSchemas;


    @JsonProperty("potentiallyPrivacyRelatedDifferencesGiven")
    public boolean hasPrivacyRelevantDifferences() {
        return generalDifferenceGiven && (
                !newEndpoints.isEmpty() ||
                !missingEndpoints.isEmpty() ||
                !changedOperations.isEmpty() ||
                !changedSchemas.isEmpty()
        );
    }
}
