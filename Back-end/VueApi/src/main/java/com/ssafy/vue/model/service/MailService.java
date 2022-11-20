package com.ssafy.vue.model.service;

public interface MailService {
	// 인증코드 생성 
	String makeCode(int size); 
	// HTML String 생성(메일 내용)
	String makeHtml(String code);
	// 메일 전송
	String sendMail(String email);

}
