package com.teamgreen.apidiff.service;

import com.teamgreen.apidiff.model.ApiDiff;
import com.teamgreen.apidiff.model.MissingEndpoint;
import com.teamgreen.apidiff.model.NewEndpoint;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiDiffAnalyzerService {

    public ApiDiff getRelevantDiffs(ChangedOpenApi completeDiff){
        if(completeDiff.isUnchanged()){
            return new ApiDiff();
        }

        List<NewEndpoint> newEndpoints = completeDiff.getNewEndpoints().stream()
                .map(NewEndpoint::new)
                .collect(Collectors.toList());

        List<MissingEndpoint> missingEndpoints = completeDiff.getMissingEndpoints().stream()
                .map(MissingEndpoint::new)
                .collect(Collectors.toList());

        return new ApiDiff(newEndpoints,missingEndpoints,completeDiff.getChangedOperations(),completeDiff.getChangedSchemas());

    }
}
