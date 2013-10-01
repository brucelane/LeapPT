import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PlayerData {
	
	JSONObject obj = new JSONObject();
	JSONArray list = new JSONArray();
	JSONParser parser = new JSONParser();
	
	PlayerData(){
		
		
	}
	public void addData() {
		
		//// add data to object
		
		/*
		 * boolean hasPlayed;
			String gameName;
			int highScore;
			int timeSpent;
			float percentComp;
			int numWins;
			int numLosses;
			int curLevel;
		*/
		obj.put("name", "player1");
		obj.put("total_time", "0:0:00");
		obj.put("rank", "noob");
		/// obj.put("rank", new Integer(100));
	 
		
		list.add("FINGERBALL"); // game name
		list.add(new Integer(0)); // high score
		list.add(new Integer(0)); // time spent
		list.add(new Float(0.00)); // percent completed
		list.add(new Integer(0)); // num wins
		list.add(new Integer(0)); // num losses
		list.add(new Integer(0)); // cur level
		
	 
		obj.put("game", list);
	 
		try {
	 
			FileWriter file = new FileWriter("data/playerData.json");
			file.write(obj.toJSONString());
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		System.out.print(obj);
	 
	}
	
	public void getData(){
		
		 
		try {
	 
			Object obj = parser.parse(new FileReader("data/playerData.json"));
	 
			JSONObject jsonObject = (JSONObject) obj;
	 
			String name = (String) jsonObject.get("name");
			System.out.println(name);
			
			String rank = (String) jsonObject.get("rank");
			System.out.println(rank);
	 
			String total_time = (String) jsonObject.get("total_time");
			System.out.println(total_time);
	 
			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("game");
			Iterator<String> iterator = msg.iterator();
			while (iterator.hasNext()) {
				System.out.println(String.valueOf(iterator.next()));
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	 
} ///end class
