package hibp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import helpers.Email;
import helpers.Network;
import helpers.Persistence;
import models.HIBPR;
import models.History;

public class HIBP {
	public static void main(String[] args) throws UnsupportedEncodingException{
		String apiEndpointBreachedAccount = "https://haveibeenpwned.com/api/v2/breachedaccount/";
		int apiRateLimitTime = 1600;
		
		History history = Persistence.loadHistory();
		ArrayList<String> addresses = Persistence.loadEMailAddresses();
		System.out.println("Loaded addresses: "+addresses.toString());
		String mailHTML = "<html>";
		
		for (String address : addresses) {
			String addressEncoded=URLEncoder.encode(address, "UTF-8");
			HIBPR hibpr = Network.getHIBPRResult(apiEndpointBreachedAccount+addressEncoded,address);
			if(hibpr.getNwFail()!=null) {
				mailHTML += hibpr.toString();
				System.out.println(hibpr.toString());
			}
			boolean pwnPrinted=false;
			for (String pwn : hibpr.getPwns()) {
				if(!history.contains(hibpr.getEmail(), pwn)) {
					history.addHistory(address, pwn);
					if(!pwnPrinted) {
						mailHTML += hibpr.toString();
						pwnPrinted=true;
						System.out.println(hibpr.toString());
					}
				}
			}
			try { TimeUnit.MILLISECONDS.sleep(apiRateLimitTime); } catch (InterruptedException e) { }
		}
		boolean sendMailSuccess = Email.sendToRecipients(mailHTML+="</html>");
		// only update history when report was confirmed
		if(sendMailSuccess) {
			Persistence.writeHistory(history);
		}
	}
}