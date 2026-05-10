package com.huntos.dto;

import lombok.Data;
import java.util.List;

@Data
public class ScoredJob {
    private int matchScore; // match_score in JSON
    private List<String> skillMatch; // skill_match in JSON
    private List<String> skillGaps; // skill_gaps in JSON
    private String summary;
    private String redFlags; // red_flags in JSON
}
