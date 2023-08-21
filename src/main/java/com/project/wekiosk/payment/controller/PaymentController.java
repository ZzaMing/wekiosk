package com.project.wekiosk.payment.controller;

import com.google.gson.Gson;
import com.project.wekiosk.fcm.service.FcmNotificationService;
import com.project.wekiosk.payment.service.PaymentService;
import com.project.wekiosk.payment.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    private final PaymentService service;

    private final FcmNotificationService fcmNotificationService;

    @GetMapping("/list/{sno}")
    public PageResponseDTO<PaymentListDTO> list(@PathVariable("sno") Long sno, PageRequestDTO requestDTO) {

        return service.list(sno, requestDTO);
    }

    @GetMapping("/{payno}")
    public PaymentDTO getOne(@PathVariable("payno") Long payno) {

        return service.getOne(payno);
    }

    @PostMapping("")
    public Long register(@RequestBody RegPaymentDTO regPaymentDTO) {

        return service.register(regPaymentDTO);
    }

    @PostMapping("/fcm")
    public void paymentFcm(@RequestBody String email){

        String decodedEmail = UriUtils.decode(email, "UTF-8");

        log.info("fcm: " + decodedEmail);

        decodedEmail = decodedEmail.substring(0, decodedEmail.length() -1);

        log.info(decodedEmail);

        fcmNotificationService.sendPaymentInfo(decodedEmail);
    }

    @PutMapping("")
    public Map<String, String> modify(@RequestBody PaymentDTO paymentDTO){

        service.modify(paymentDTO);

        return Map.of("result", "success");
    }

    @GetMapping("/sales/{sno}")
    public Map<String, Long> getSales(@PathVariable("sno") Long sno, @RequestParam("date") LocalDate date){

        return service.getSales(sno, date);
    }

}
