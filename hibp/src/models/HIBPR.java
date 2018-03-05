package models;

import java.util.ArrayList;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

public class HIBPR {
	private boolean pwnd;
	private String nwFail; 
	private String email;
	private ArrayList<String> pwns = new ArrayList<String>();
	
	public HIBPR(String email, boolean pwnd) {
		this.setEmail(email);
		this.pwnd=pwnd;
	}
	
	public HIBPR(String email, boolean pwnd, String nwFail) {
		this.setEmail(email);
		this.pwnd=pwnd;
		this.setNwFail(nwFail);
	}
	
	public void addPwn(String pwn) {
		pwns.add(pwn);
		this.pwnd=true;
	}
	public ArrayList<String> getPwns(){
		return pwns;
	}
	public boolean isPwnd() {
		return pwnd;
	}
	public void setPwnd(boolean pwnd) {
		this.pwnd = pwnd;
	}
	public String getNwFail() {
		return nwFail;
	}
	public void setNwFail(String nwFail) {
		this.nwFail = nwFail;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String toString() {
		if(nwFail!=null) {
			return email+" "+nwFail+"<br>";
		}
		String res = (pwnd?"<b>pwnd!</b>":"ok")+" "+email+"<br>";
		if(pwnd) {
			for (String pwn : pwns) {
				res += pwn+" - <a href=\"https://haveibeenpwned.com/PwnedWebsites#"+pwn.replaceAll("\\s+","")+"\">Info about Breach</a>"+"<br>";
			}
		}
		return res;
	}
	
	public static HIBPR jsonToHIBPR(String address, String string) {
		JsonArray pwnsArray = Json.parse(string).asArray();
		HIBPR hibpr = new HIBPR(address,true);
		for (JsonValue pwns : pwnsArray) {
			hibpr.addPwn(pwns.asObject().get("Name").asString());
		}
		return hibpr;
	}
}
