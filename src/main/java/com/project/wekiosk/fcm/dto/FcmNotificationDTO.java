package com.project.wekiosk.fcm.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FcmNotificationDTO {

    private Long targetUserId;
    private String title;
    private String body;
    private String image;
}
