package com.teamgreen.apidiff.model;

import lombok.Data;

import java.util.List;

@Data
public class ApiDiffTira {
    private Object newGlobalTiraAnnotation;
    private Object missingGlobalTiraAnnotation;
    private Object changedGlobalTiraAnnotation;

    private List<ApiTiraAnnotations.SchemaTiraAnnotation> newComponentTiraAnnotations;
    private List<ApiTiraAnnotations.SchemaTiraAnnotation> missingComponentTiraAnnotations;
    private List<ApiTiraAnnotations.SchemaTiraAnnotation> changedComponentTiraAnnotations;
}
