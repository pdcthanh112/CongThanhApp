package com.congthanh.paymentservice.service.strategy;

import com.congthanh.paymentservice.constant.enums.PaymentMethod;
import com.congthanh.paymentservice.constant.enums.PaymentStatus;
import com.congthanh.paymentservice.model.entity.Payment;
import com.congthanh.paymentservice.model.request.PaymentRequest;
import com.congthanh.paymentservice.model.request.RefundRequest;
import com.congthanh.paymentservice.model.response.PaymentResponse;
import com.congthanh.paymentservice.model.response.PaymentVNPayResponse;
import com.congthanh.paymentservice.model.response.RefundResponse;
import com.congthanh.paymentservice.repository.payment.PaymentRepository;
import com.congthanh.paymentservice.service.VNPayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
@Slf4j
public class VNPayPaymentStrategy{

    private final PaymentRepository paymentRepository;

    public static final String VNPAY_VERSION = "2.1.0";
    public static final String VNPAY_COMMAND = "pay";
    public static final long DEFAULT_MULTIPLIER = 100L;

    @Value("${payment.vnpay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${payment.vnpay.hashSecret}")
    private String vnp_HashSecret;

    @Value("${payment.vnpay.payUrl}")
    private String vnp_PayUrl;

    @Value("${payment.vnpay.returnUrl}")
    private String vnp_ReturnUrl;

//    private final CryptoService cryptoService;

//    @Override
    public PaymentMethod paymentMethod() {
        return PaymentMethod.VN_PAY;
    }

//    @Override
    public PaymentVNPayResponse processPayment(PaymentRequest request, String ipAddress) {

        String vnp_OrderInfo = request.getOrderId();
        String vnp_OrderType = "other";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
//        String vnp_IpAddr = request.getAdditionalInfo().get("ip_address") != null ? request.getAdditionalInfo().get("ip_address") : "127.0.0.1";
        String vnp_IpAddr = ipAddress;
        String vnp_Locale = "vn";
        String vnp_CurrCode = "VND";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPAY_VERSION);
        vnp_Params.put("vnp_Command", VNPAY_COMMAND);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(request.getAmount().multiply(BigDecimal.valueOf(DEFAULT_MULTIPLIER)) ));
        vnp_Params.put("vnp_CurrCode", vnp_CurrCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan hoa don" + vnp_OrderInfo + "Nap tien cho thue bao 0123456789. So tien 100,000 VND");
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        String vnp_SecureHash = VNPayConfig.hashAllFields(vnp_Params, vnp_HashSecret);
        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);

        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : vnp_Params.entrySet()) {
            try {
                query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                        .append("&");
            }catch (Exception e){
                log.error("Error encoding parameter: " + entry.getKey(), e);
            }
        }
        query.deleteCharAt(query.length() - 1);

        String paymentUrl = vnp_PayUrl + "?" + query;
        return new PaymentVNPayResponse(paymentUrl, "00", "Success");
    }

    public boolean verifyPayment(Map<String, String> params, String secureHash) {
        String calculatedHash = VNPayConfig.hashAllFields(params, vnp_HashSecret);
        return calculatedHash.equals(secureHash);
    }

//    @Override
    public PaymentResponse executePayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        return null;
    }

//    @Override
    public PaymentResponse refundPayment(String paymentId, RefundRequest request) {
        return null;
    }

//    @Override
    public void validatePayment(PaymentRequest request) {

    }

//    @Override
    public RefundResponse processRefund(RefundRequest request) {
        return null;
    }
}
