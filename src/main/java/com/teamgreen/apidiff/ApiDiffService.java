package com.teamgreen.apidiff;

import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApiDiffService {

    @Autowired
    private ApiDiffRenderService apiDiffRenderService;

    public String getApiDiffWithBasicRender(String renderType, OpenApiPair openApiPair) {
        ChangedOpenApi diff = OpenApiCompare.fromContents(openApiPair.getOldApiSpec().toString(), openApiPair.getNewApiSpec().toString());
        return apiDiffRenderService.getRenderedApiDiff(diff,renderType);
    }


}
