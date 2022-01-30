package com.teamgreen.apidiff.service;

import com.teamgreen.apidiff.model.ApiDiff;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.openapitools.openapidiff.core.output.ConsoleRender;
import org.openapitools.openapidiff.core.output.HtmlRender;
import org.openapitools.openapidiff.core.output.JsonRender;
import org.openapitools.openapidiff.core.output.MarkdownRender;
import org.springframework.stereotype.Service;

@Service
public class ApiDiffResponseService {

    public String getRenderedApiDiffResponse(ChangedOpenApi diff, String renderType){
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

    public String getResponseForPrivacyRelatedChanges(ApiDiff apiDiff){
        return "";
    }
}
