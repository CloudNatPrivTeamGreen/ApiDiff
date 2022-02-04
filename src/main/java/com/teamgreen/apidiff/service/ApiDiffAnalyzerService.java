package com.teamgreen.apidiff.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamgreen.apidiff.model.*;
import io.swagger.v3.oas.models.OpenAPI;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ApiDiffAnalyzerService {

    private static ObjectMapper mapper = new ObjectMapper();

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

        List<ChangedOperation> changedOperations = completeDiff.getChangedOperations().stream()
                .map(ChangedOperation::new)
                .collect(Collectors.toList());


        return new ApiDiff(newEndpoints, missingEndpoints, changedOperations, completeDiff.getChangedSchemas());

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
        List<SchemaTiraAnnotation> schemaTiraAnnotations = new ArrayList<>();

        openAPI.getComponents().getSchemas().forEach((s, schema) -> {
            if (schema.getExtensions() != null)
                schemaTiraAnnotations.add(new SchemaTiraAnnotation(s, mapper.convertValue(schema.getExtensions().get("x-tira"), JsonNode.class)));
        });

        JsonNode globalTiraAnnotations = (openAPI.getExtensions() != null) ? mapper.convertValue(openAPI.getExtensions().get("x-tira"), JsonNode.class) : null;
        return new ApiTiraAnnotations(globalTiraAnnotations, schemaTiraAnnotations);

    }

    private void analyzeGlobalTiraAnnotations(ApiDiffTira apiDiffTira, ApiTiraAnnotations oldTiraAnnotations, ApiTiraAnnotations newTiraAnnotations) {
        JsonNode oldAnnotations = oldTiraAnnotations.getGlobalTiraAnnotations();
        JsonNode newAnnotations = newTiraAnnotations.getGlobalTiraAnnotations();

        if (oldAnnotations == null) {
            apiDiffTira.setNewGlobalTiraAnnotation(newAnnotations);
            return;
        } else if (newAnnotations == null) {
            apiDiffTira.setMissingGlobalTiraAnnotation(oldAnnotations);
            return;
        }

        //New global annotations
        newAnnotations.fields().forEachRemaining(entry -> {
                    if (!oldAnnotations.has(entry.getKey()))
                        apiDiffTira.setNewGlobalTiraAnnotation(mapper.valueToTree(entry));
                }
        );

        //Missing global annotations
        oldAnnotations.fields().forEachRemaining(entry -> {
                    if (!newAnnotations.has(entry.getKey())) {
                        apiDiffTira.setMissingGlobalTiraAnnotation(mapper.valueToTree(entry));
                    } else {
                        //Check for changes in global annotations which were present in both specifications
                        if (!entry.getValue().equals(newAnnotations.get(entry.getKey()))) {
                            apiDiffTira.setChangedGlobalTiraAnnotation(
                                    new ChangedGlobalTiraAnnotation(
                                            mapper.valueToTree(entry),
                                            newAnnotations.get(entry.getKey())
                                    )
                            );
                        }
                    }
                }
        );
    }

    private void analyzeSchemaTiraAnnotations(ApiDiffTira apiDiffTira, ApiTiraAnnotations oldTiraAnnotations, ApiTiraAnnotations newTiraAnnotations) {

        List<SchemaTiraAnnotation> oldAnnotations = oldTiraAnnotations.getSchemaTiraAnnotations();
        List<SchemaTiraAnnotation> newAnnotations = newTiraAnnotations.getSchemaTiraAnnotations();

        //New Schema annotations
        apiDiffTira.setNewSchemaTiraAnnotations(
                newAnnotations.stream()
                        .filter(newSchemaTiraAnnotation ->
                                oldAnnotations.stream()
                                        .noneMatch(oldSchemaTiraAnnotation ->
                                                oldSchemaTiraAnnotation.getSchemaName().equals(newSchemaTiraAnnotation.getSchemaName()))
                        )
                        .collect(Collectors.toList())
        );

        //Missing Schema annotations
        apiDiffTira.setMissingSchemaTiraAnnotations(
                oldAnnotations.stream()
                        .filter(oldSchemaTiraAnnotation ->
                                newAnnotations.stream()
                                        .noneMatch(newSchemaTiraAnnotation ->
                                                newSchemaTiraAnnotation.getSchemaName().equals(oldSchemaTiraAnnotation.getSchemaName()))
                        )
                        .collect(Collectors.toList())
        );

        //Check for changes in schema annotations which were present in both specifications
        apiDiffTira.setChangedSchemaTiraAnnotations(
                oldAnnotations.stream()
                        .filter(oldSchemaTiraAnnotation ->
                                newAnnotations.stream()
                                        .anyMatch(newSchemaTiraAnnotation ->
                                                newSchemaTiraAnnotation.getSchemaName().equals(oldSchemaTiraAnnotation.getSchemaName())
                                                        && !newSchemaTiraAnnotation.getSchemaTiraAnnotation().equals(oldSchemaTiraAnnotation.getSchemaTiraAnnotation()))
                        )
                        .map(oldSchemaTiraAnnotation -> new ChangedSchemaTiraAnnotation(
                                oldSchemaTiraAnnotation.getSchemaName(),
                                oldSchemaTiraAnnotation.getSchemaTiraAnnotation(),
                                newAnnotations.stream()
                                        .filter(newSchemaTiraAnnotation -> newSchemaTiraAnnotation.getSchemaName().equals(oldSchemaTiraAnnotation.getSchemaName()))
                                        .map(SchemaTiraAnnotation::getSchemaTiraAnnotation)
                                        .findFirst().orElse(null)))
                        .collect(Collectors.toList())
        );
    }

}
