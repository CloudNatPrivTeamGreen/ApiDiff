package com.teamgreen.apidiff.service;

import com.teamgreen.apidiff.model.ApiDiff;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.springframework.stereotype.Service;

@Service
public class ApiDiffAnalyzerService {

    public ApiDiff getRelevantDiffs(ChangedOpenApi completeDiff){
        if(completeDiff.isUnchanged()){
            return ApiDiff.builder()
                    .generalDifferenceGiven(false)
                    .build();
        }

        return ApiDiff.builder()
                .generalDifferenceGiven(true)
                .newEndpoints(completeDiff.getNewEndpoints())
                .missingEndpoints(completeDiff.getMissingEndpoints())
                .changedOperations(completeDiff.getChangedOperations())
                .changedSchemas(completeDiff.getChangedSchemas())
                .build();
    }
}
