package com.teamgreen.apidiff.controller;


import com.teamgreen.apidiff.model.ApiDiff;
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

    @GetMapping("/render")
    public String getAllApiChangesWithBasicRender(@RequestParam String type, @RequestBody ApiSpecPair apiSpecPair){
        return apiDiffService.getAllApiChangesWithBasicRender(type, apiSpecPair);
    }

    @GetMapping("/relevantChanges")
    public ApiDiff getPotentiallyPrivacyRelatedChanges(@RequestBody ApiSpecPair apiSpecPair){
        return apiDiffService.getPotentiallyPrivacyRelatedChanges(apiSpecPair);
    }

    @GetMapping("/relevantChangesFromSamples")
    public ApiDiff getPotentiallyPrivacyRelatedChangesFromSamples(@RequestParam String oldApi, @RequestParam String newApi){
        return apiDiffService.getPotentiallyPrivacyRelatedChangesExample(oldApi, newApi);
    }


}


