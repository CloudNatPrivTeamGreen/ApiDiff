package com.teamgreen.apidiff.service;

import com.teamgreen.apidiff.model.ApiDiff;
import com.teamgreen.apidiff.model.ApiSpecPair;
import com.teamgreen.apidiff.model.ApiDiffTira;
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


    public ApiDiff getPotentiallyPrivacyRelatedChanges(ApiSpecPair apiSpecPair) {
        ChangedOpenApi completeDiff = OpenApiCompare.fromContents(apiSpecPair.getOldApiSpec().toString(), apiSpecPair.getNewApiSpec().toString());

        return apiDiffAnalyzerService.getRelevantDiffs(completeDiff);
    }

    public ApiDiff getPotentiallyPrivacyRelatedChangesExample(String oldApi, String newApi) {
        String OPENAPI_DOC1 = System.getProperty("user.dir")+"\\yamls\\"+oldApi;
        String OPENAPI_DOC2 = System.getProperty("user.dir")+"\\yamls\\"+newApi;
        ChangedOpenApi completeDiff = OpenApiCompare.fromLocations(OPENAPI_DOC1, OPENAPI_DOC2);

        return apiDiffAnalyzerService.getRelevantDiffs(completeDiff);
    }

    public ApiDiffTira getTiraChanges(ApiSpecPair apiSpecPair) {
        ChangedOpenApi completeDiff = OpenApiCompare.fromContents(apiSpecPair.getOldApiSpec().toString(), apiSpecPair.getNewApiSpec().toString());

        return apiDiffAnalyzerService.getTiraDiffs(completeDiff);
    }

    public ApiDiffTira getTiraChangesFromSamples(String oldApi, String newApi) {
        String OPENAPI_DOC1 = System.getProperty("user.dir")+"\\yamls\\"+oldApi;
        String OPENAPI_DOC2 = System.getProperty("user.dir")+"\\yamls\\"+newApi;
        ChangedOpenApi completeDiff = OpenApiCompare.fromLocations(OPENAPI_DOC1, OPENAPI_DOC2);

        return apiDiffAnalyzerService.getTiraDiffs(completeDiff);
    }
}
