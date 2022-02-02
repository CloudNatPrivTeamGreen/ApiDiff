package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ChangedSchemaTiraAnnotation {
    private String schemaName;
    private JsonNode oldSchemaTiraAnnotation;
    private JsonNode newSchemaTiraAnnotation;

    public ChangedSchemaTiraAnnotation(String schemaName, JsonNode oldSchemaTiraAnnotation, JsonNode newSchemaTiraAnnotation) {
        this.schemaName = schemaName;
        this.oldSchemaTiraAnnotation = oldSchemaTiraAnnotation;
        this.newSchemaTiraAnnotation = newSchemaTiraAnnotation;
    }

}
