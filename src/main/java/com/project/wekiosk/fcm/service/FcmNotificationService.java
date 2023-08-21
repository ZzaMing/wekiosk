package com.project.wekiosk.fcm.service;

import com.project.wekiosk.fcm.dto.FcmNotificationDTO;

public interface FcmNotificationService {

    void sendLoginInfo(String email);

    void sendPaymentInfo(String email);
}
