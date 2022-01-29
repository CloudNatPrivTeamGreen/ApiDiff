package com.teamgreen.apidiff.service;

import com.teamgreen.apidiff.model.ApiDiff;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.springframework.stereotype.Service;

@Service
public class ApiDiffAnalyzerService {

    public ApiDiff analyze(ChangedOpenApi completeDiff){
        if(!isGeneralDifferenceGiven(completeDiff)){
            return ApiDiff.builder()
                    .generalDifference(false)
                    .privacyRelevantDifferences(false)
                    .build();
        }

        return ApiDiff.builder()
                .generalDifference(true)
                .build();
    }

    private boolean isGeneralDifferenceGiven(ChangedOpenApi completeDiff){
        return completeDiff.isDifferent();
    }

}
