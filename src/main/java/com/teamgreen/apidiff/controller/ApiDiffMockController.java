package com.teamgreen.apidiff.controller;

import lombok.NoArgsConstructor;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import org.openapitools.openapidiff.core.output.HtmlRender;
import org.openapitools.openapidiff.core.output.JsonRender;
import org.openapitools.openapidiff.core.output.MarkdownRender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mock")
@NoArgsConstructor
public class ApiDiffMockController {

    private static final String OPENAPI_DOC1 = System.getProperty("user.dir")+"\\yamls\\issue-256_1.json";
    private static final String OPENAPI_DOC2 = System.getProperty("user.dir")+"\\yamls\\issue-256_2.json";

    @GetMapping("/console")
    public String mockGetDiffWithConsoleOutput(){
        ChangedOpenApi diff = OpenApiCompare.fromLocations(OPENAPI_DOC1, OPENAPI_DOC2);

        return new ConsoleRender().render(diff);
    }

    @GetMapping("/html")
    public String mockGetDiffWithHtmlOutput(){
        ChangedOpenApi diff = OpenApiCompare.fromLocations(OPENAPI_DOC1, OPENAPI_DOC2);

        return new HtmlRender("Changelog",
                "http://deepoove.com/swagger-diff/stylesheets/demo.css")
                .render(diff);
    }

    @GetMapping("/json")
    public String mockGetDiffWithJsonOutput(){
        ChangedOpenApi diff = OpenApiCompare.fromLocations(OPENAPI_DOC1, OPENAPI_DOC2);

        return new JsonRender().render(diff);
    }

    @GetMapping("/markdown")
    public String mockGetDiffWithMarkdownOutput(){
        ChangedOpenApi diff = OpenApiCompare.fromLocations(OPENAPI_DOC1, OPENAPI_DOC2);

        return new MarkdownRender().render(diff);
    }

}
