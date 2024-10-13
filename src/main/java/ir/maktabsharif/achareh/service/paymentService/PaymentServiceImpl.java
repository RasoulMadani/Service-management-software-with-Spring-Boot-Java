package ir.maktabsharif.achareh.service.paymentService;

import ir.maktabsharif.achareh.dto.order.OrderDetailsDto;
import ir.maktabsharif.achareh.dto.payment.PaymentInfoDto;
import ir.maktabsharif.achareh.entity.Financial;
import ir.maktabsharif.achareh.entity.Order;
import ir.maktabsharif.achareh.entity.User;
import ir.maktabsharif.achareh.exception.RuleException;
import ir.maktabsharif.achareh.repository.FinancialJpaRepository;
import ir.maktabsharif.achareh.repository.OrderJpaRepository;
import ir.maktabsharif.achareh.service.bankService.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final BankService bankService;
    private final OrderJpaRepository orderJpaRepository;
    private final FinancialJpaRepository financialJpaRepository;
    @Value("${arcaptcha.secret}")
    private String arcaptchaSecret;

    @Value("${arcaptcha.site}")
    private String arcaptchaSite;
    @Override
    public String processPayment(PaymentInfoDto paymentInfoDto, Model model, String arcaptchaToken) {
        //        if (!validateArcaptcha(arcaptchaToken)) {
//            model.addAttribute("error", "Invalid Arcaptcha. Please try again.");
//            return "payment";
//        }
//        bankService.peyment(paymentInfoDto);

        System.out.println(paymentInfoDto);
        model.addAttribute("message", "Payment processed successfully!");
        return "payment-confirmation";
    }

    @Override

    public void showOrderDetails(Long orderId, Model model) {
        Order order =
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto(
                order.getId(),
                order.getDescription(),
                order.getSuggestion().getSuggestionPrice(),
                order.getSuggestion().getUser().getId(),
                order.getUser().getId()
        );

        model.addAttribute("orderDetailsDto",orderDetailsDto);
    }

    @Override
    public void getFinacialUserAndSuggestionPrice(Long orderId, Model model) {
        Order order =
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));

        User user = order.getUser();

        Financial financial = financialJpaRepository.findById(user.getId())
                            .orElseThrow(() -> new RuleException("financial.not.found"));

        model.addAttribute("financialUser", financial.getAmount().longValue());
        model.addAttribute("suggestionPrice", order.getSuggestion().getSuggestionPrice().longValue());
    }

    @Override
    public void processCreditPayment(Long orderId, Model model) {
        Order order =
                orderJpaRepository.findById(orderId)
                        .orElseThrow(() -> new RuleException("order.not.found"));

        User user = order.getUser();

        Financial financial = financialJpaRepository.findById(user.getId())
                .orElseThrow(() -> new RuleException("financial.not.found"));

    }

    private boolean validateArcaptcha(String arcaptchaToken) {
        String arcaptchaVerifyUrl = "https://api.arcaptcha.com/verify";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> body = new HashMap<>();
        body.put("secret_key", arcaptchaSecret);
        body.put("site_key", arcaptchaSite);
        body.put("challenge_id", arcaptchaToken);

        Map<String, Object> response = restTemplate.postForObject(arcaptchaVerifyUrl, body, Map.class);
        return (Boolean) response.get("success");
    }
}
