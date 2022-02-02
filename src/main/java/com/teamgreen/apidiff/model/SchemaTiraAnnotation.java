package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class SchemaTiraAnnotation {
    private String schemaName;
    private JsonNode schemaTiraAnnotation;

    public SchemaTiraAnnotation(String schemaName, JsonNode schemaTiraAnnotation) {
        this.schemaName = schemaName;
        this.schemaTiraAnnotation = schemaTiraAnnotation;
    }
}
