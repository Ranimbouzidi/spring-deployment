package tn.esprit.cloud_in_mypocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// hedhy la classe de reponse
public class RevenuePredictionResponse {
    private double totalRevenueLast30Days;
    private double totalRevenuePrevious30Days;
    private double averageDailyRevenue;
    private double predictedRevenueNext30Days;
    private double growthRate;
    private String tendance;
}
