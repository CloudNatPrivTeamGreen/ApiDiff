package com.teamgreen.apidiff;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/apidiff")
@NoArgsConstructor
public class ApiDiffController {

    @Autowired
    private ApiDiffService apiDiffService;

    @GetMapping("/render")
    public String getApiDiffWithBasicRender(@RequestParam String type, @RequestBody OpenApiPair openApiPair){
        return apiDiffService.getApiDiffWithBasicRender(type,openApiPair);
    }


}

