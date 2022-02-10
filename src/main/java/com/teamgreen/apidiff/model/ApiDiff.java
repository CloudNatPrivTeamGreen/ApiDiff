package com.teamgreen.apidiff.model;

import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class ApiDiff {
    private boolean generalDifferenceGiven;
    private boolean potentiallyPrivacyRelatedDifferencesGiven;
    private List<NewEndpoint> newEndpoints = new ArrayList<>();
    private List<MissingEndpoint> missingEndpoints = new ArrayList<>();
    private List<ChangedOperation> changedOperations = new ArrayList<>();

    public ApiDiff() {
        this.generalDifferenceGiven = false;
    }

    public ApiDiff(List<NewEndpoint> newEndpoints, List<MissingEndpoint> missingEndpoints, List<ChangedOperation> changedOperations) {
        this.newEndpoints = newEndpoints;
        this.missingEndpoints = missingEndpoints;
        this.changedOperations = changedOperations;
        this.generalDifferenceGiven = true;
        this.potentiallyPrivacyRelatedDifferencesGiven = !newEndpoints.isEmpty() || !missingEndpoints.isEmpty() || !changedOperations.isEmpty();
    }
}
