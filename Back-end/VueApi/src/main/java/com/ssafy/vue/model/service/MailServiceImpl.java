package com.ssafy.vue.model.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ssafy.vue.controller.UserController;

@Service
public class MailServiceImpl implements MailService {
	
	// ! 필요 사전작업 
	//   i) pom.xml 내 dependency 추가 
	//   ii) application.properties 설정 추가 (보낼 이메일 setting)
	//   iii) naver SMTP 서버 사용
	
	@Autowired
	private JavaMailSender mailSender;
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	// 인증코드 생성
	@Override
	public String makeCode(int size) {
		Random random = new Random(); 
		StringBuffer sb = new StringBuffer(); 
		
		int code = 0;
		// 1. 랜덤으로 48부터 122까지의 숫자를 뽑는다. 
		//    - 뽑은 숫자가 소문자(48~57), 숫자(65~90), 대문자(97~122)일 경우 char로 변환하여 sb로 더한다.
		//	  - 나머지 숫자를 뽑았을 경우 continue로 다시 뽑기
		// 2. sb 길이가 size와 같다면 return
		do {
			code = random.nextInt(75) + 48; 
			if ((code >= 48 && code <= 57) || (code >= 65 && code <= 90) || (code >= 97 && code <= 122)) {
				sb.append((char) code);
			}
			else {
				continue; 
			}
		}
		while (sb.length() < size);
			
		return sb.toString(); 
	}
	
	// HTML String 생성 (메일 내용)
	@Override
	public String makeHtml(String code) {
		String html = "<h2> SONPUM 회원 인증 </h2> <br> 아래의 코드를 화면에 입력해주세요! <br>" + code; 
		
		return html;
	}

	// 메일 전송
	@Override
	public String sendMail(String email) {
		logger.info("# MailServiceImpl - sendMail 호출");
		
		// 1. 인증코드 생성
		String code = makeCode(6);
		String html = makeHtml(code);
		String subject = "SONPUM 회원 인증";
		logger.info("# 메일 전송을 위한 생성된 코드 확인 - code: {}", code);
		
		// 2. 메일 전송 
		MimeMessage mail = mailSender.createMimeMessage(); 
		try {
			mail.setSubject(subject, "utf-8");
			mail.setText(html, "utf-8", "html");
			mail.addRecipient(RecipientType.TO, new InternetAddress(email));
			logger.info("# 메일 전송 확인 - {}", mail);
			mailSender.send(mail);
			
		} catch (MessagingException e) {
			e.printStackTrace();
			return "error";
		}
		
		return code; 
	}

}
