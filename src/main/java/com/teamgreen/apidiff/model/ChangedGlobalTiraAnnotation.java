package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ChangedGlobalTiraAnnotation {
    private JsonNode oldGlobalTiraAnnotation;
    private JsonNode newGlobalTiraAnnotation;

    public ChangedGlobalTiraAnnotation(JsonNode oldGlobalTiraAnnotation, JsonNode newGlobalTiraAnnotation) {
        this.oldGlobalTiraAnnotation = oldGlobalTiraAnnotation;
        this.newGlobalTiraAnnotation = newGlobalTiraAnnotation;
    }
}
