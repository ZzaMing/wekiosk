package com.project.wekiosk.member.service;

import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface SocialService {

    List<String> getKakaoEmail(String authCode);
}
