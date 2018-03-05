package models;

import java.util.ArrayList;

public class PwnHistory {
	private String email;
	private ArrayList<String> pwns = new ArrayList<String>();
	
	
	public PwnHistory(String email, String pwn) {
		this.setEmail(email);
		pwns.add(pwn);
	}
	
	
	public PwnHistory(String email, ArrayList<String> pwns) {
		this.setEmail(email);
		this.setPwns(pwns);
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<String> getPwns() {
		return pwns;
	}
	public void setPwns(ArrayList<String> pwns) {
		this.pwns = pwns;
	}
	public void addPwn(String pwn) {
		this.pwns.add(pwn);
	}
}
