package models;

import java.util.ArrayList;

public class History {
	private ArrayList<PwnHistory> pwnhistorylist = new ArrayList<PwnHistory>();

	public ArrayList<PwnHistory>  getPwnHistory() {
		return pwnhistorylist;
	}
	
	public void addHistory(String email, String pwn) {
		boolean found = false;
		PwnHistory pwnhistoryToAdd = null;
		for (PwnHistory pwnhistory : pwnhistorylist) {
			if(pwnhistory.getEmail().equals(email)) {
				found = true;
				for (String pwns : pwnhistory.getPwns()) {
					if(!pwns.equals(pwn)) {
						pwnhistoryToAdd = pwnhistory;
					}
				}
			}
		}
		if(pwnhistoryToAdd!=null) {
			pwnhistoryToAdd.addPwn(pwn);
		}
		if(!found) {
			pwnhistorylist.add(new PwnHistory(email, pwn));
		}
	}
	
	public boolean contains(String email, String pwn) {
		for (PwnHistory pwnhistory : pwnhistorylist) {
			if(pwnhistory.getEmail().equals(email)) {
				for (String pwnz : pwnhistory.getPwns()) {
					if(pwnz.equals(pwn)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
