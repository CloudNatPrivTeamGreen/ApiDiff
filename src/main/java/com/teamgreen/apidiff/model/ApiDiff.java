package com.teamgreen.apidiff.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiDiff {
    private boolean generalDifference;
    private boolean privacyRelevantDifferences;
}
