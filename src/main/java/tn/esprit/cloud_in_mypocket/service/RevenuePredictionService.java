package tn.esprit.cloud_in_mypocket.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.cloud_in_mypocket.dto.RevenuePredictionResponse;
import tn.esprit.cloud_in_mypocket.entity.Paiement;
import tn.esprit.cloud_in_mypocket.repository.PaiementRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenuePredictionService {
    private final PaiementRepository paiementRepository;

    @Transactional
    public RevenuePredictionResponse predictRevenueForUser(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysAgo = today.minusDays(30);
        LocalDate sixtyDaysAgo = today.minusDays(60);

        // Revenus récents (J-30 à aujourd'hui)
        List<Paiement> recentPaiements = paiementRepository
                .findByUtilisateurIdAndDatePaiementBetween(userId, thirtyDaysAgo, today);
        double totalRevenueLast30Days = recentPaiements.stream()
                .mapToDouble(Paiement::getMontant)
                .sum();

        // Revenus précédents (J-60 à J-30)
        List<Paiement> previousPaiements = paiementRepository
                .findByUtilisateurIdAndDatePaiementBetween(userId, sixtyDaysAgo, thirtyDaysAgo);
        double totalRevenuePrevious30Days = previousPaiements.stream()
                .mapToDouble(Paiement::getMontant)
                .sum();

        // Moyenne journalière
        double averageDailyRevenue = totalRevenueLast30Days / 30;

        // Prédiction
        double predictedRevenueNext30Days = averageDailyRevenue * 30;

        // Croissance
        double growthRate = 0;
        if (totalRevenuePrevious30Days != 0) {
            growthRate = ((totalRevenueLast30Days - totalRevenuePrevious30Days) / totalRevenuePrevious30Days) * 100;
        }

        // Tendance
        String tendance = "Stable";
        if (growthRate > 5) tendance = "En hausse 📈";
        else if (growthRate < -5) tendance = "En baisse 📉";

        return new RevenuePredictionResponse(
                totalRevenueLast30Days,
                totalRevenuePrevious30Days,
                averageDailyRevenue,
                predictedRevenueNext30Days,
                growthRate,
                tendance
        );
    }
}
