package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class ApiDiffTira {
    private JsonNode newGlobalTiraAnnotation;
    private JsonNode missingGlobalTiraAnnotation;
    private JsonNode changedGlobalTiraAnnotation;

    private List<ApiTiraAnnotations.SchemaTiraAnnotation> newSchemaTiraAnnotations;
    private List<ApiTiraAnnotations.SchemaTiraAnnotation> missingSchemaTiraAnnotations;
    private List<ApiTiraAnnotations.SchemaTiraAnnotation> changedSchemaTiraAnnotations;
}
