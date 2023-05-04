package com.ashokit.utility;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	
	@Autowired
	private JavaMailSender mailsender;
	
public boolean sendEmail(String subject,String body,String to) {
		
		try {
			MimeMessage mimsg = mailsender.createMimeMessage();
			
			MimeMessageHelper msgHelper = new MimeMessageHelper(mimsg,true);
			
			msgHelper.setSubject(subject);
			msgHelper.setText(body,true);
			msgHelper.setTo(to);
			
			

			mailsender.send(mimsg);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
		
	}

}
