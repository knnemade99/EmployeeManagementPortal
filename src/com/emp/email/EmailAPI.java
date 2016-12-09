package com.emp.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
  
@Service("email")
public class EmailAPI {
 
	@Autowired
	private MailSender mail; 
 
	public void sendEmail(String toAddress, String subject, String msgBody) {
 System.out.println(toAddress+subject+msgBody);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("mihirkhote@gmail.com");
		msg.setTo(toAddress);
		msg.setSubject(subject);
		msg.setText(msgBody);
		mail.send(msg);
	}
}