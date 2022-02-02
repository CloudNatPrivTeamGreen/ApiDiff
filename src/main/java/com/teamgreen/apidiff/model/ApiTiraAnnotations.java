package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import java.util.List;

@Data
public class ApiTiraAnnotations {

    @Data
    public static class SchemaTiraAnnotation {
        private String schemaName;
        private JsonNode schemaTiraAnnotation;

        public SchemaTiraAnnotation(String schemaName, JsonNode schemaTiraAnnotation) {
            this.schemaName = schemaName;
            this.schemaTiraAnnotation = schemaTiraAnnotation;
        }
    }

    private JsonNode globalTiraAnnotations;
    private List<SchemaTiraAnnotation> schemaTiraAnnotations;

    public ApiTiraAnnotations(JsonNode globalTiraAnnotations, List<SchemaTiraAnnotation> schemaTiraAnnotations) {
        this.globalTiraAnnotations = globalTiraAnnotations;
        this.schemaTiraAnnotations = schemaTiraAnnotations;
    }
}
