package com.teamgreen.apidiff;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class OpenApiPair {
    private JsonNode oldApiSpec;
    private JsonNode newApiSpec;
}
