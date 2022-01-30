package com.teamgreen.apidiff.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ApiSpecPair {
    private JsonNode oldApiSpec;
    private JsonNode newApiSpec;
}
