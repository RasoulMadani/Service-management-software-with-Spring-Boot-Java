package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.payment.PaymentInfoDto;
import ir.maktabsharif.achareh.service.orderService.OrderService;
import ir.maktabsharif.achareh.service.paymentService.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;

    @GetMapping
    public String showPaymentPage(Model model) {
        model.addAttribute("paymentInfo",new PaymentInfoDto());
       return "payment";
    }

    @GetMapping("/order/{orderId}")
    public String showOrderDetailsPage(@PathVariable Long orderId,Model model) {
        paymentService.showOrderDetails(orderId,model);

        return "order-details";
    }
    @GetMapping("/method")
    public String showPaymentMethodPage(Model model) {
        model.addAttribute("paymentInfo",new PaymentInfoDto());
        return "payment-method";
    }
    @PostMapping("/paymentMethodSelectedDetails")
    public String paymentMethod(
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam("order_id") Long orderId,
            Model model
    ) {
        if ("credit".equals(paymentMethod)) {
            System.out.println(orderId);
            paymentService.getFinacialUserAndSuggestionPrice(orderId,model);
            model.addAttribute("message", "پرداخت اعتباری انتخاب شد.");

            return "credit-payment";
        } else if ("online".equals(paymentMethod)) {
            model.addAttribute("paymentInfo",new PaymentInfoDto());
            return "online-payment";

//            model.addAttribute("message", "پرداخت آنلاین انتخاب شد.");

        }

        return null;
    }

    @PostMapping("/process-credit-payment")
    public String processCreditPayment(@RequestParam("order_id") Long orderId,Model model) {
         paymentService.processCreditPayment(orderId,model);
         return "payment-confirmation";
    }

    @PostMapping("/process-payment")
    public String processPayment(@Valid @ModelAttribute PaymentInfoDto paymentInfo, Model model, String arcaptchaToken) {
      return   paymentService.processPayment(paymentInfo,model,arcaptchaToken);
    }

}
