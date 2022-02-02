package com.teamgreen.apidiff.model;

import lombok.Data;

import java.util.List;

@Data
public class ApiDiffTira {
    private Object newGlobalTiraAnnotation;
    private Object missingGlobalTiraAnnotation;
    private Object changedGlobalTiraAnnotation;

    private List<ApiTiraAnnotations.SchemaTiraAnnotation> newSchemaTiraAnnotations;
    private List<ApiTiraAnnotations.SchemaTiraAnnotation> missingSchemaTiraAnnotations;
    private List<ApiTiraAnnotations.SchemaTiraAnnotation> changedSchemaTiraAnnotations;
}
