package com.teamgreen.apidiff.model;

import lombok.Data;
import java.util.List;

@Data
public class ApiTiraAnnotations {

    @Data
    public static class SchemaTiraAnnotation {
        private String schemaName;
        private Object schemaTiraAnnotation;

        public SchemaTiraAnnotation(String schemaName, Object schemaTiraAnnotation) {
            this.schemaName = schemaName;
            this.schemaTiraAnnotation = schemaTiraAnnotation;
        }
    }

    private Object globalTiraAnnotations;
    private List<SchemaTiraAnnotation> schemaTiraAnnotations;

    public ApiTiraAnnotations(Object globalTiraAnnotations, List<SchemaTiraAnnotation> schemaTiraAnnotations) {
        this.globalTiraAnnotations = globalTiraAnnotations;
        this.schemaTiraAnnotations = schemaTiraAnnotations;
    }
}
