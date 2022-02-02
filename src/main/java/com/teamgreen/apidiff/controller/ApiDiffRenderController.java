package com.teamgreen.apidiff.controller;

import com.teamgreen.apidiff.model.ApiSpecPair;
import lombok.NoArgsConstructor;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import org.openapitools.openapidiff.core.output.HtmlRender;
import org.openapitools.openapidiff.core.output.JsonRender;
import org.openapitools.openapidiff.core.output.MarkdownRender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/render")
@NoArgsConstructor
public class ApiDiffRenderController {

    @GetMapping("/fromSamples")
    public String getDiffWithBasicRenderFromSamples(@RequestParam String type, @RequestParam String oldApi, @RequestParam String newApi){
        String OPENAPI_DOC1 = System.getProperty("user.dir")+"\\yamls\\"+oldApi;
        String OPENAPI_DOC2 = System.getProperty("user.dir")+"\\yamls\\"+newApi;

        ChangedOpenApi diff = OpenApiCompare.fromLocations(OPENAPI_DOC1, OPENAPI_DOC2);

        return getRenderedApiDiffResponse(diff,type);
    }


    @GetMapping("/fromRequestBody")
    public String getAllApiChangesWithBasicRender(@RequestParam String type, @RequestBody ApiSpecPair apiSpecPair){
        ChangedOpenApi diff = OpenApiCompare.fromContents(apiSpecPair.getOldApiSpec().toString(), apiSpecPair.getNewApiSpec().toString());

        return getRenderedApiDiffResponse(diff,type);
    }

    private String getRenderedApiDiffResponse(ChangedOpenApi diff, String renderType){
        String response;
        if ("json".equals(renderType)) {
            response = new JsonRender().render(diff);
        } else if ("html".equals(renderType)) {
            response = new HtmlRender("Changelog", "http://deepoove.com/swagger-diff/stylesheets/demo.css").render(diff);
        } else if ("console".equals(renderType)) {
            response = new ConsoleRender().render(diff);
        } else if ("markdown".equals(renderType)) {
            response = new MarkdownRender().render(diff);
        } else {
            response = "Invalid render type! Supported types are: json, html, console and markdown";
        }
        return response;
    }



}
