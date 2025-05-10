package tn.esprit.cloud_in_mypocket.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.cloud_in_mypocket.dto.PaymentAIDTO;
import tn.esprit.cloud_in_mypocket.dto.SubscriptionAIDTO;
import tn.esprit.cloud_in_mypocket.dto.UserAIDTO;
import tn.esprit.cloud_in_mypocket.entity.*;
import tn.esprit.cloud_in_mypocket.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5000")
public class AIDataController {

    private final UserRepository userRepository;
    private final PackAbonnementRepository packAbonnementRepository;
    private final SubscriptionHistoryRepository subscriptionHistoryRepository;
    private final PaiementRepository paiementRepository;

    @GetMapping("/users")
    public List<UserAIDTO> getAllUsersForAI() {
        List<User> users = userRepository.findAll();
        return users.stream()
            .map(this::convertToUserAIDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/payments")
    public List<PaymentAIDTO> getAllPaymentsForAI() {
        List<Paiement> payments = paiementRepository.findAll();
        return payments.stream()
            .map(this::convertToPaymentAIDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/subscriptions")
    public List<SubscriptionAIDTO> getAllSubscriptionsForAI() {
        List<SubscriptionHistory> subscriptions = subscriptionHistoryRepository.findAll();
        return subscriptions.stream()
            .map(this::convertToSubscriptionAIDTO)
            .collect(Collectors.toList());
    }

    private UserAIDTO convertToUserAIDTO(User user) {
        UserAIDTO dto = new UserAIDTO();
        dto.setUserId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setNumeroDeTelephone(user.getNumeroDeTelephone());
        dto.setRole(user.getRole().name());
        dto.setActive(user.getActive());
        dto.setEmailVerified(user.getEmailVerified());
        dto.setCreatedAt(user.getLastLoginDate());
        return dto;
    }

    private PaymentAIDTO convertToPaymentAIDTO(Paiement payment) {
        PaymentAIDTO dto = new PaymentAIDTO();
        dto.setPaymentId(payment.getId());
        dto.setUserId(payment.getUtilisateur().getId());
        dto.setPackId(payment.getPackAbonnement().getId());
        dto.setMontant(payment.getMontant());
        dto.setMethode(payment.getMethode());
        dto.setDatePaiement(payment.getDatePaiement());
        dto.setStatus(payment.getStatus());
        dto.setIsYearly(payment.getIsYearly());
        return dto;
    }

    private SubscriptionAIDTO convertToSubscriptionAIDTO(SubscriptionHistory subscription) {
        SubscriptionAIDTO dto = new SubscriptionAIDTO();
        dto.setSubscriptionId(subscription.getId());
        dto.setUserId(subscription.getUser().getId());
        dto.setPackId(subscription.getPackAbonnement().getId());
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        dto.setStatus("active");
        return dto;
    }
} 