package tn.esprit.cloud_in_mypocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationSummaryDTO {
    private String type;
    private int count;
    private String priority;
} 