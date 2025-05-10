package tn.esprit.cloud_in_mypocket.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.cloud_in_mypocket.dto.RevenuePredictionResponse;
import tn.esprit.cloud_in_mypocket.service.RevenuePredictionService;

@RestController
@RequestMapping("/api/revenue")
@RequiredArgsConstructor
public class RevenuePredictionController {

    private final RevenuePredictionService revenuePredictionService;

    @GetMapping("/predict/{userId}")
    public ResponseEntity<RevenuePredictionResponse> predictRevenue(@PathVariable Long userId) {
        RevenuePredictionResponse response = revenuePredictionService.predictRevenueForUser(userId);
        return ResponseEntity.ok(response);
    }

}
