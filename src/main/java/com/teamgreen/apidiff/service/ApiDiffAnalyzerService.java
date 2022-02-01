package com.teamgreen.apidiff.service;

import com.teamgreen.apidiff.model.*;
import io.swagger.v3.oas.models.OpenAPI;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ApiDiffAnalyzerService {

    public ApiDiff getRelevantDiffs(ChangedOpenApi completeDiff) {
        if (completeDiff.isUnchanged()) {
            return new ApiDiff();
        }

        List<NewEndpoint> newEndpoints = completeDiff.getNewEndpoints().stream()
                .map(NewEndpoint::new)
                .collect(Collectors.toList());

        List<MissingEndpoint> missingEndpoints = completeDiff.getMissingEndpoints().stream()
                .map(MissingEndpoint::new)
                .collect(Collectors.toList());

        return new ApiDiff(newEndpoints, missingEndpoints, completeDiff.getChangedOperations(), completeDiff.getChangedSchemas());

    }

    public ApiDiffTira getTiraDiffs(ChangedOpenApi completeDiff) {
        ApiTiraAnnotations oldTiraAnnotations = getTiraAnnotations(completeDiff.getOldSpecOpenApi());
        ApiTiraAnnotations newTiraAnnotations = getTiraAnnotations(completeDiff.getNewSpecOpenApi());

        ApiDiffTira apiDiffTira = new ApiDiffTira();
        analyzeGlobalTiraAnnotations(apiDiffTira, oldTiraAnnotations, newTiraAnnotations);
        analyzeSchemaTiraAnnotations(apiDiffTira, oldTiraAnnotations, newTiraAnnotations);

        return apiDiffTira;
    }

    private ApiTiraAnnotations getTiraAnnotations(OpenAPI openAPI) {
        List<ApiTiraAnnotations.SchemaTiraAnnotation> schemaTiraAnnotations = new ArrayList<>();
        openAPI.getComponents().getSchemas().forEach((s, schema) -> {
            if (schema.getExtensions() != null)
                schemaTiraAnnotations.add(new ApiTiraAnnotations.SchemaTiraAnnotation(s, schema.getExtensions().get("x-tira")));
        });

        Object globalTiraAnnotations = (openAPI.getExtensions() != null) ? openAPI.getExtensions().get("x-tira") : null;
        return new ApiTiraAnnotations(globalTiraAnnotations, schemaTiraAnnotations);

    }

    private void analyzeGlobalTiraAnnotations(ApiDiffTira apiDiffTira, ApiTiraAnnotations oldTiraAnnotations, ApiTiraAnnotations newTiraAnnotations) {
        //Missing Global Annotations
        if (oldTiraAnnotations.getGlobalTiraAnnotations() != null && newTiraAnnotations.getGlobalTiraAnnotations() == null) {
            apiDiffTira.setMissingGlobalTiraAnnotation(oldTiraAnnotations.getGlobalTiraAnnotations());
        } //New Global Annotations
        else if (oldTiraAnnotations.getGlobalTiraAnnotations() == null && newTiraAnnotations.getGlobalTiraAnnotations() != null) {
            apiDiffTira.setNewGlobalTiraAnnotation(newTiraAnnotations.getGlobalTiraAnnotations());
        }//Already present or changed Global Annotations
        else if (oldTiraAnnotations.getGlobalTiraAnnotations() == null && newTiraAnnotations.getGlobalTiraAnnotations() == null) {
//            TODO: Add changed global Tira Annotation
//            apiDiffTira.setChangedGlobalTiraAnnotation(null);
        }
    }

    private void analyzeSchemaTiraAnnotations(ApiDiffTira apiDiffTira, ApiTiraAnnotations oldTiraAnnotations, ApiTiraAnnotations newTiraAnnotations) {

        //Already present or changed schema Annotations
        apiDiffTira.setChangedComponentTiraAnnotations(
                oldTiraAnnotations.getSchemaTiraAnnotations().stream()
                        .flatMap(oldSchemaTiraAnnotation ->
                                newTiraAnnotations.getSchemaTiraAnnotations().stream()
                                .filter(newSchemaTiraAnnotation ->
                                        newSchemaTiraAnnotation.getSchemaName().equals(oldSchemaTiraAnnotation.getSchemaName()))
                        )
                        .collect(Collectors.toList())
        );
    }

    private boolean isTiraAnnotationPresentInComponent() {
        return true;
    }


}
