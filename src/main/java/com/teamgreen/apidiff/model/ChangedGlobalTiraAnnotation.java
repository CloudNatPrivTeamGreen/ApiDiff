package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ChangedGlobalTiraAnnotation {
    private String key;
    private JsonNode oldGlobalTiraAnnotation;
    private JsonNode newGlobalTiraAnnotation;

    public ChangedGlobalTiraAnnotation(String key, JsonNode oldGlobalTiraAnnotation, JsonNode newGlobalTiraAnnotation) {
        this.key = key;
        this.oldGlobalTiraAnnotation = oldGlobalTiraAnnotation;
        this.newGlobalTiraAnnotation = newGlobalTiraAnnotation;
    }
}
