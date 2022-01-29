package com.teamgreen.apidiff;

import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import org.openapitools.openapidiff.core.output.HtmlRender;
import org.openapitools.openapidiff.core.output.JsonRender;
import org.openapitools.openapidiff.core.output.MarkdownRender;
import org.springframework.stereotype.Service;

@Service
public class ApiDiffRenderService {

    public String getRenderedApiDiff(ChangedOpenApi diff, String renderType){
        String response;
        if (renderType.equals("json")) {
            response = new JsonRender().render(diff);
        } else if (renderType.equals("html")) {
            response = new HtmlRender("Changelog", "http://deepoove.com/swagger-diff/stylesheets/demo.css").render(diff);
        } else if (renderType.equals("console")) {
            response = new ConsoleRender().render(diff);
        } else if (renderType.equals("markdown")) {
            response = new MarkdownRender().render(diff);
        } else {
            response = "Invalid render type! Supported types are: json, html, console and markdown";
        }
        return response;
    }
}
