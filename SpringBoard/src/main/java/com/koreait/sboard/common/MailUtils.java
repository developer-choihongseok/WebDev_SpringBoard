package com.koreait.sboard.common;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtils {
	
	private String userId;
	private String userPw;
	private String host;
	private String port;
	private String fromEmail;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	
	public int sendFindPwEmail(final String toEmail, final String user_id, final String code) {
		String subject = "sboard 비밀번호 찾기 인증 이메일 입니다.";
		
		StringBuilder sb = new StringBuilder();
		
		// 체인 기법 : 자기 자신의 주소값을 리턴하기 때문에, '체인 기법'이라 부른다.
		sb.append("<div>")
		.append("<a href=\"http://localhost:8090/user/findPwAuth?cd=")	// \" : 쌍 따옴표 표시하게 해준다.
		.append(code)
		.append("&user_id=")
		.append(user_id)
		.append("\" target=\"_blank\">비밀번호 변경하러 가기</a>")	// \" : 쌍 따옴표 표시하게 해준다.
		.append("</div>");
		
		// <div><a href="http://localhost:9090/user/findPwAuth?cd=6767&user_id=seok" target="_blank">비밀번호 변경하러 가기</a></div>
		
		return sendMail(toEmail, subject, sb.toString());
	}
	
	// 매개변수 줄 때, final 주는 걸 추천!!
	// 메일 보내는 용도
	public int sendMail(final String toEmail, final String subject, final String body) {
		try {
			Properties prop = new Properties();
			
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.host", host);	// 이메일 발송을 처리해줄 STMP 서버를 나타낸다.
			prop.put("mail.smtp.ssl.trust", host);
			
			// SMTP 서버 정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스를 생성
			Session session = Session.getDefaultInstance(prop, new Authenticator() {
				protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
					return new javax.mail.PasswordAuthentication(userId, userPw);
				}
			});
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(body, "text/html; charset=UTF-8");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			
			message.setContent(multipart);
			Transport.send(message);
			
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
