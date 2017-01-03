package com.emp.util;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sms")
public class SmsAPI{
    public String ACCOUNT_SID ;
    public String AUTH_TOKEN ;
    public String TWILIO_NUMBER ;
    
    
    
    public String getACCOUNT_SID() {
		return ACCOUNT_SID;
	}
	public void setACCOUNT_SID(String aCCOUNT_SID) {
		ACCOUNT_SID = aCCOUNT_SID;
	}
	public String getAUTH_TOKEN() {
		return AUTH_TOKEN;
	}
	public void setAUTH_TOKEN(String aUTH_TOKEN) {
		AUTH_TOKEN = aUTH_TOKEN;
	}
	public String getTWILIO_NUMBER() {
		return TWILIO_NUMBER;
	}
	public void setTWILIO_NUMBER(String tWILIO_NUMBER) {
		TWILIO_NUMBER = tWILIO_NUMBER;
	}

	public void sendSms(String to , String body){
    	try{
	    	TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	    	 List<NameValuePair> params = new ArrayList<NameValuePair>();
	    	 params.add(new BasicNameValuePair("Body", body));
	         params.add(new BasicNameValuePair("To", to)); //Add real number here
	         params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
	  
	         MessageFactory messageFactory = client.getAccount().getMessageFactory();
	         Message message = messageFactory.create(params);
    	}
    	catch(Exception e){
    		System.out.println("SMS not sent "+e.toString());
    	}
    }
}