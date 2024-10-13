package ir.maktabsharif.achareh.service.paymentService;

import ir.maktabsharif.achareh.dto.payment.PaymentInfoDto;
import org.springframework.ui.Model;

public interface PaymentService {
    String processPayment(PaymentInfoDto paymentInfo, Model model, String arcaptchaToken);

    void showOrderDetails(Long orderId, Model model);

    void getFinacialUserAndSuggestionPrice(Long orderId, Model model);

    void processCreditPayment(Long orderId, Model model);
}
