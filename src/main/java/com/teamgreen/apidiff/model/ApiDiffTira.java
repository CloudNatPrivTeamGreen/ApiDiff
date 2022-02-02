package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
public class ApiDiffTira {
    private JsonNode newGlobalTiraAnnotation;
    private JsonNode missingGlobalTiraAnnotation;
    private JsonNode changedGlobalTiraAnnotation;

    private List<SchemaTiraAnnotation> newSchemaTiraAnnotations;
    private List<SchemaTiraAnnotation> missingSchemaTiraAnnotations;
    private List<ChangedSchemaTiraAnnotation> changedSchemaTiraAnnotations;
}
