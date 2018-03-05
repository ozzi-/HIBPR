package helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import models.HIBPR;

public class Network {
	public static HIBPR getHIBPRResult(String getURL, String address) {
		StringBuilder result = new StringBuilder();
		int respCode = 0;
		URL url;
		try {
			url = new URL(getURL);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET"); 
			conn.setRequestProperty("User-Agent",
					"HIBPR - Java Report - https://github.com/ozzi-/HIBPR");
			respCode = conn.getResponseCode();
			BufferedReader rd;
			String line;
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			conn.disconnect();
		} catch (Exception e) {
			if(respCode!=0) {
				if(respCode==429) {
					return new HIBPR(address,false, "Too many attempts for this address");
				}else if(respCode==404){
					return new HIBPR(address,false);
				}
			}else {
				if(e instanceof FileNotFoundException) {
					return new HIBPR(address,false);
				}else {
					e.printStackTrace();
					return new HIBPR(address, false, "Unknown error ("+respCode+") - "+e.getMessage());
				}
			}
		}
		return HIBPR.jsonToHIBPR(address, result.toString());
	}


}
