package com.teamgreen.apidiff.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.teamgreen.apidiff.model.ApiDiff;
import com.teamgreen.apidiff.model.ApiDiffTira;
import com.teamgreen.apidiff.service.ApiDiffService;
import com.teamgreen.apidiff.model.ApiSpecPair;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/apidiff")
@NoArgsConstructor
public class ApiDiffController {

    private ApiDiffService apiDiffService;

    @Autowired
    public ApiDiffController(ApiDiffService apiDiffService) {
        this.apiDiffService = apiDiffService;
    }

    @GetMapping("/relevantChanges")
    public ApiDiff getPotentiallyPrivacyRelatedChangesJson(@RequestBody ApiSpecPair apiSpecPair){
        return apiDiffService.getPotentiallyPrivacyRelatedChanges(apiSpecPair);
    }

    @GetMapping(value = "/relevantChanges", consumes = "application/x-yaml")
    public ApiDiff getPotentiallyPrivacyRelatedChangesYaml(@RequestBody String apiSpecPair) throws JsonProcessingException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());

        return apiDiffService.getPotentiallyPrivacyRelatedChanges(yamlReader.readValue(apiSpecPair, ApiSpecPair.class));
    }

    @GetMapping("/relevantChangesFromSamples")
    public ApiDiff getPotentiallyPrivacyRelatedChangesFromSamples(@RequestParam String oldApi, @RequestParam String newApi){
        return apiDiffService.getPotentiallyPrivacyRelatedChangesExample(oldApi, newApi);
    }

    @GetMapping("/tira")
    public ApiDiffTira getTiraChangesJson(@RequestBody ApiSpecPair apiSpecPair){
        return apiDiffService.getTiraChanges(apiSpecPair);
    }

    @GetMapping(value = "/tira", consumes = "application/x-yaml")
    public ApiDiffTira getTiraChangesYaml(@RequestBody String apiSpecPair) throws JsonProcessingException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());

        return apiDiffService.getTiraChanges(yamlReader.readValue(apiSpecPair, ApiSpecPair.class));
    }

    @GetMapping("/tiraFromSamples")
    public ApiDiffTira getTiraChangesFromSamples(@RequestParam String oldApi, @RequestParam String newApi){
        return apiDiffService.getTiraChangesFromSamples(oldApi, newApi);
    }


}


