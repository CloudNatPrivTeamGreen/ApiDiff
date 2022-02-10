package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiDiffTira {
    private List<JsonNode> newGlobalTiraAnnotation = new ArrayList<>();
    private List<JsonNode> missingGlobalTiraAnnotation = new ArrayList<>();
    private List<ChangedGlobalTiraAnnotation> changedGlobalTiraAnnotation = new ArrayList<>();

    private List<SchemaTiraAnnotation> newSchemaTiraAnnotations = new ArrayList<>();
    private List<SchemaTiraAnnotation> missingSchemaTiraAnnotations = new ArrayList<>();
    private List<ChangedSchemaTiraAnnotation> changedSchemaTiraAnnotations = new ArrayList<>();


}
