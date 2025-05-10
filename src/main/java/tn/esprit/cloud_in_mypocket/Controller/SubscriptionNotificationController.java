package tn.esprit.cloud_in_mypocket.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.cloud_in_mypocket.entity.Paiement;
import tn.esprit.cloud_in_mypocket.service.SubscriptionNotificationService;
import tn.esprit.cloud_in_mypocket.dto.ResponseDTO;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class SubscriptionNotificationController {

    @Autowired
    private SubscriptionNotificationService subscriptionNotificationService;

    @PostMapping("/check-expiring")
    public ResponseEntity<ResponseDTO> checkExpiringSubscriptions() {
        try {
            subscriptionNotificationService.checkExpiringSubscriptions();
            return ResponseEntity.ok(new ResponseDTO("success", "Vérification des abonnements expirants effectuée avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO("error", "Erreur lors de la vérification des abonnements: " + e.getMessage()));
        }
    }

    @PostMapping("/{paiementId}/send-renewal-confirmation")
    public ResponseEntity<ResponseDTO> sendRenewalConfirmation(@PathVariable Long paiementId) {
        try {
            Paiement paiement = subscriptionNotificationService.getPaiementById(paiementId);
            if (paiement == null) {
                return ResponseEntity.notFound().build();
            }
            subscriptionNotificationService.sendRenewalConfirmation(paiement);
            return ResponseEntity.ok(new ResponseDTO("success", "Confirmation de renouvellement envoyée avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO("error", "Erreur lors de l'envoi de la confirmation: " + e.getMessage()));
        }
    }

    @PostMapping("/test-notification")
    public ResponseEntity<ResponseDTO> sendTestNotification(
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String packName,
            @RequestParam int daysUntilExpiration) {
        try {
            subscriptionNotificationService.sendTestNotification(email, username, packName, daysUntilExpiration);
            return ResponseEntity.ok(new ResponseDTO("success", "Notification de test envoyée avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO("error", "Erreur lors de l'envoi de la notification: " + e.getMessage()));
        }
    }
} 