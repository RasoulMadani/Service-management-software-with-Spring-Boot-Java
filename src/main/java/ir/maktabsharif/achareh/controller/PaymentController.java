package ir.maktabsharif.achareh.controller;

import ir.maktabsharif.achareh.dto.order.OrderResponseDto;
import ir.maktabsharif.achareh.dto.payment.PaymentInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Value("${arcaptcha.secret}")
    private String arcaptchaSecret;

    @Value("${arcaptcha.site}")
    private String arcaptchaSite;
    @GetMapping
    public String showPaymentPage(Model model) {
        model.addAttribute("paymentInfo",new PaymentInfo());
       return "payment";
    }

    @PostMapping("/process-payment")
    public String processPayment(@ModelAttribute PaymentInfo paymentInfo, Model model,String arcaptchaToken) {

        if (!validateArcaptcha(arcaptchaToken)) {
            model.addAttribute("error", "Invalid Arcaptcha. Please try again.");
            return "payment";  // اگر نامعتبر است، بازگشت به فرم پرداخت
        }
        // انجام عملیات پرداخت (پردازش داده‌های فرم)
        // مثلا ارسال به سرویس پرداخت یا ذخیره در دیتابیس
        System.out.println(paymentInfo);
        model.addAttribute("message", "Payment processed successfully!");
        return "payment-confirmation";  // نمایش صفحه تایید پرداخت
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
