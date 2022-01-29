package com.teamgreen.apidiff.service;

import com.teamgreen.apidiff.model.ApiDiff;
import com.teamgreen.apidiff.model.ApiSpecPair;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApiDiffService {

    private ApiDiffResponseService apiDiffResponseService;
    private ApiDiffAnalyzerService apiDiffAnalyzerService;

    @Autowired
    public ApiDiffService(ApiDiffResponseService apiDiffResponseService, ApiDiffAnalyzerService apiDiffAnalyzerService) {
        this.apiDiffResponseService = apiDiffResponseService;
        this.apiDiffAnalyzerService = apiDiffAnalyzerService;
    }

    public String getAllApiChangesWithBasicRender(String renderType, ApiSpecPair apiSpecPair) {
        ChangedOpenApi completeDiff = OpenApiCompare.fromContents(apiSpecPair.getOldApiSpec().toString(), apiSpecPair.getNewApiSpec().toString());
        return apiDiffResponseService.getRenderedApiDiffResponse(completeDiff,renderType);
    }


    public String getPotentiallyPrivacyRelatedChanges(ApiSpecPair apiSpecPair) {
        ChangedOpenApi completeDiff = OpenApiCompare.fromContents(apiSpecPair.getOldApiSpec().toString(), apiSpecPair.getNewApiSpec().toString());
        ApiDiff analyzedDiff = apiDiffAnalyzerService.analyze(completeDiff);
        return apiDiffResponseService.getResponseForPrivacyRelatedChanges(analyzedDiff);
    }
}
