import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

import processing.core.PApplet;


public class PlayerProfile {
	 private static PlayerProfile instance = null;
	 public static PApplet pApp;
	 
	 // app profile
	 AppProfile theAppProfile;

	 JSONParser parser = new JSONParser();
	 
	 
	 String playerName;
	 String playerRank;
	 String totalTime; /// totalTime played
	 int difficultyLevel;
	 
	 int statLength; /// length of the stat list
	 
	 ///// stats for all games played /////
	 ///// name, current level, high score, time spent playing, % completed, num wins, num losses ///// 
	 ArrayList<GameProfile> GameStats;

	 GameProfile theGameProf;
	
	 protected PlayerProfile() {
	      // Exists only to defeat instantiation.
		 theAppProfile = theAppProfile.getInstance();
		 //
		 GameStats = new ArrayList();
		 // get saved player data
		 getPlayerData();
		 

	 }
	 
	 public static PlayerProfile getInstance() {
      if(instance == null) {
         instance = new PlayerProfile();
      }
      	return instance;
	 }
	 
	 
	 public void getPlayerData(){
		 try {
			 
				Object obj = parser.parse(new FileReader("data/playerData.json"));
		 
				JSONObject jsonObject = (JSONObject) obj;
		 
				String name = (String) jsonObject.get("name");
				playerName =  name;
				
				String rank = (String) jsonObject.get("rank");
				playerRank = rank;
				
				///*
				String difficulty = (String) jsonObject.get("difficulty");
				difficultyLevel = Integer.parseInt(difficulty);
				/// */
				
				String total_time = (String) jsonObject.get("total_time");
				totalTime = total_time;
		 
				// create a new game profile
				// add stats to it
				// add the game profile to the 
				// game profile array
				
				for (int i=0; i < theAppProfile.gameMode.size(); i++){
					
					JSONArray msg = (JSONArray) jsonObject.get(theAppProfile.gameMode.get(i));
					theGameProf = new GameProfile();
					/*
					 * list.add(new Integer(0)); // high score
					list.add(new Integer(0)); // time spent
					list.add(new Float(0.00)); // percent completed
					list.add(new Integer(0)); // num wins
					list.add(new Integer(0)); // num losses
					list.add(new Integer(0)); // cur level
					*/
					
					theGameProf.gameName = theAppProfile.gameMode.get(i);
					// System.out.println(theGameProf.gameName);
					
					for(int j=0; j< msg.size(); j++){
						
						if(j==0){
							theGameProf.highScore = ((Long) msg.get(j)).intValue();
						}
						if(j ==1){
							theGameProf.timeSpent = ((Long) msg.get(j)).intValue();
						}
						if(j ==2){
							theGameProf.percentComp = ((Double) msg.get(j)).floatValue();
						}
						if(j ==3){
							theGameProf.numWins = ((Long) msg.get(j)).intValue();
						}
						if(j ==4){
							theGameProf.numLosses = ((Long) msg.get(j)).intValue();
						}
						if(j ==5){
							theGameProf.curLevel = ((Long) msg.get(j)).intValue();
						}
						//// extra data
						if(j ==6){
							theGameProf.gameInfo = ((String) msg.get(j));
						}
						if(j ==7){
							theGameProf.gameThumbnail = ((String) msg.get(j));
						}
						if(j ==8){
							theGameProf.gameMainPic = ((String) msg.get(j));
						}
					}
					
					GameStats.add(theGameProf);
					
					/*
					System.out.println(theGameProf.highScore);
					System.out.println(theGameProf.timeSpent);
					System.out.println(theGameProf.percentComp);
			 		System.out.println(theGameProf.numWins);			
		 			System.out.println(theGameProf.numLosses);			
					System.out.println(theGameProf.curLevel);
					*/
					
					statLength = msg.size(); /// save length of stat list for writing to the json file later
					System.out.println("Stat length: " + statLength + " " + msg.size());
				}
				 
		 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		/// savePlayerData();
	 }
	 
	 public void savePlayerData() {
			
			JSONObject obj = new JSONObject();
			obj.put("name", playerName);
			obj.put("total_time", totalTime);
			obj.put("difficulty", String.valueOf(difficultyLevel));
			obj.put("rank", playerRank);
			/// iterate thru the game array
			System.out.println("Game mode length: " + statLength + theAppProfile.gameMode.size());
			for(int i=0; i<theAppProfile.gameMode.size(); i++){
				
				JSONArray list = new JSONArray();
				for(int j=0; j<statLength; j++){
					
					if(j == 0){
						if(GameStats.get(i).curScore > GameStats.get(i).highScore){
							GameStats.get(i).highScore = GameStats.get(i).curScore;
						}
						list.add(GameStats.get(i).highScore);
						// System.out.println(GameStats.get(i).highScore);
					}
					if(j == 1){
						list.add(GameStats.get(i).timeSpent);
						// System.out.println(GameStats.get(i).timeSpent);
					}
					if(j == 2){
						list.add(GameStats.get(i).percentComp);
						// System.out.println(GameStats.get(i).percentComp);
					}
					if(j == 3){
						list.add(GameStats.get(i).numWins);
						// System.out.println(GameStats.get(i).numWins);
					}
					if(j == 4){
						list.add(GameStats.get(i).numLosses);
						// System.out.println(GameStats.get(i).numLosses);
					}
					if(j == 5){
						list.add(GameStats.get(i).curLevel);
						// System.out.println(GameStats.get(i).curLevel);
					}
					
				}
				obj.put(GameStats.get(i).gameName, list);
				System.out.println("Saving data: " + GameStats.get(i).gameName + " " + list);
			}

			try {
		 
				FileWriter file = new FileWriter("data/playerData.json");
				file.write(obj.toJSONString());
				file.flush();
				file.close();
		 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	 
	 
	 public void intGameStats(){
		 
		 //// load stat file

		 
		 //// populate all stats
		 
		 
	 }
	 
	 //// add all games to stats, even if not played
	 //// name, current level, high score, time spent playing, % completed, num wins, num losses ///// 
	 public void addGameToStats(boolean hp, String gn, int cl, int hs, int ts, float pc, int nw, int nl){
		 
		 /// theGameProf = new GameProfile(hp, gn, cl, hs, ts, pc, nw, nl);
		 theGameProf = new GameProfile();
		 GameStats.add(theGameProf);
		 
	 }
	 
	 public void updateStats(boolean hp, String gn, int cl, int hs, int ts, float pc, int nw, int nl){
		 
		 //// do loading anim
		 
		 for (int i=0; i<GameStats.size(); i++){
			 
			 GameProfile tGP = GameStats.get(i);
			 
			 if(tGP.gameName!= null && tGP.gameName == gn){
				 
				 //// update stats in Array
				 
			 }

		 }
	 }

	
	
	
	

} /// end class
