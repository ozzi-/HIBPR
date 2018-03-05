package helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import hibp.HIBP;
import models.History;
import models.PwnHistory;

public class Persistence {
	public static ArrayList<String> loadEMailAddresses() {
		ArrayList<String> addresses = new ArrayList<String>();
		try {
			InputStream is = HIBP.class.getClass().getResourceAsStream("/emailaddresses.json");
			String content = convertStreamToString(is);
			is.close();
			JsonArray addressArray = Json.parse(content).asArray();
			for (JsonValue address : addressArray) {
				addresses.add(address.asString());
			}
		} catch (Exception e) {
			System.out.println("Error loading emailaddresses.json - Aborting");
			System.exit(-1);
		}
		return addresses;
	}

	public static String convertStreamToString(java.io.InputStream is) {
		if (is == null) {
			return "";
		}
		java.util.Scanner s = new java.util.Scanner(is);
		s.useDelimiter("\\A");
		String streamString = s.hasNext() ? s.next() : "";
		s.close();
		return streamString;
	}

	
	public static History loadHistory() {
		History history = new History();
		InputStream is=null;
		try {
			is = HIBP.class.getClass().getResourceAsStream("/history.json");
			String content = convertStreamToString(is);
			is.close();
			if(Json.parse(content).asArray().isEmpty()) {
				return history;
			}
			JsonObject historyObj = Json.parse(content).asArray().get(0).asObject();
			for (String entry : historyObj.names()) {
				JsonArray entryObj = historyObj.asObject().get(entry).asArray();
				for (JsonValue entryValue : entryObj) {
					history.addHistory(entry,entryValue.asString());
				}
			}
		} catch (Exception e) {
			System.out.println("Error parsing history file:");
			if(is==null) {
				System.out.println("history.json not found.");
			}else {
				System.out.println(e.getMessage()+" -> "+e.getCause());
			}
			System.out.println("Aborting");
			System.exit(-1);
		}
		return history;
	}
	
	public static void writeHistory(History history) {
		JsonObject historyObject = Json.object();
		for (PwnHistory pwnhistory : history.getPwnHistory()) {
			JsonArray pwns = new JsonArray();
			for (String pwn : pwnhistory.getPwns()) {
				pwns.add(pwn);
			}
			historyObject.add(pwnhistory.getEmail(), pwns);
		}
		JsonArray historyArray = new JsonArray().add(historyObject);
		File file;
		try {
			URL resourceUrl = HIBP.class.getClass().getResource("/history.json");
			file = new File(resourceUrl.toURI());
			OutputStream output = new FileOutputStream(file);
			output.write(historyArray.toString().getBytes(Charset.forName("UTF-8")));
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
