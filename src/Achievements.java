import java.util.ArrayList;
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

public class Achievements {
	
	ArrayList<String> CheevoNames;
	ArrayList<String> CheevoDescription;
	ArrayList<String> CheevoImage;
	
	JSONParser parser = new JSONParser();
	
	AppProfile theAppProfile;
	
	Achievements(){
		
		theAppProfile = theAppProfile.getInstance();
		
	}
	
	
	public void loadCheevos(){
		 try {
			 
				Object obj = parser.parse(new FileReader("data/cheevoData.json"));
		 
				JSONObject jsonObject = (JSONObject) obj;
		 

				/// String rank = (String) jsonObject.get("rank");
				
				
				///*
				/// String difficulty = (String) jsonObject.get("difficulty");
				// difficultyLevel = Integer.parseInt(difficulty);
				/// */
				// JSONArray cheevotype = (JSONArray) jsonObject.get("ACHIEVEMENTS");
				
				for (int i=0; i < jsonObject.size(); i++){
					
						JSONArray gameData = (JSONArray) jsonObject.get(theAppProfile.gameMode.get(i));
						String gameName = theAppProfile.gameMode.get(i);
						System.out.println(gameName);
						
						/*
						for(int j=0; j<gameData.size(); j++){

							String name = (String) gameData.get(i).get("name");
							String description = (String) jsonObject.get("description");
							String imagePath = (String) jsonObject.get("image");

							System.out.println(gameData);
							System.out.println(name);
							System.out.println(description);
							System.out.println(imagePath);
							
						}
						*/
						
						
						
						
				}
								
								/*
				
				for(int j=0; j< cheevotype.size(); j++){
					
						CheevoNames.add((String) cheevotype.get(j));
						
						CheevoNames.add((String) cheevotype.get(j));
						
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
						
						}
						if(j ==7){
							theGameProf.gameThumbnail = ((String) msg.get(j));
						}
						if(j ==8){
							theGameProf.gameMainPic = ((String) msg.get(j));
						}
					}
					
					
					/*
					System.out.println(theGameProf.highScore);
					System.out.println(theGameProf.timeSpent);
					System.out.println(theGameProf.percentComp);
			 		System.out.println(theGameProf.numWins);			
		 			System.out.println(theGameProf.numLosses);			
					System.out.println(theGameProf.curLevel);
					*/
					
					//statLength = msg.size(); /// save length of stat list for writing to the json file later
					// System.out.println("Stat length: " + statLength + " " + msg.size());

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		/// savePlayerData();
		
		
	}
	
	
	

} /// class
