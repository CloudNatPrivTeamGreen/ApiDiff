package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import java.util.List;

@Data
public class ApiTiraAnnotations {

    private JsonNode globalTiraAnnotations;
    private List<SchemaTiraAnnotation> schemaTiraAnnotations;

    public ApiTiraAnnotations(JsonNode globalTiraAnnotations, List<SchemaTiraAnnotation> schemaTiraAnnotations) {
        this.globalTiraAnnotations = globalTiraAnnotations;
        this.schemaTiraAnnotations = schemaTiraAnnotations;
    }
}
