import java.io.*;
import java.util.*;

import java.util.ArrayList;
import processing.core.PApplet;


public class PlayerProfile {
	

	PlayerData thePlayerData;
	 private static PlayerProfile instance = null;
	 public static PApplet pApp;
	 
	 int difficultyLevel;
	 
	 String currentRank;
	 
	 ///// stats for all games played /////
	 ///// name, current level, high score, time spent playing, % completed, num wins, num losses ///// 
	 ArrayList<GameProfile> GameStats;

	 GameProfile theGameProf;
	
	 protected PlayerProfile() {
	      // Exists only to defeat instantiation.
		 
		 
		 thePlayerData = new PlayerData();
		 thePlayerData.addData();
	 }
	 
	 public static PlayerProfile getInstance() {
      if(instance == null) {
         instance = new PlayerProfile();
      }
      	return instance;
	 }
	 
	 public void intGameStats(){
		 
		 //// load stat file

		 
		 //// populate all stats
		 
		 
	 }
	 
	 //// add all games to stats, even if not played
	 //// name, current level, high score, time spent playing, % completed, num wins, num losses ///// 
	 public void addGameToStats(boolean hp, String gn, int cl, int hs, int ts, float pc, int nw, int nl){
		 
		 theGameProf = new GameProfile(hp, gn, cl, hs, ts, pc, nw, nl);
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
		 
		 String playerData = "saved data";
		 /// save all stats to file
		 PrintStream out = null;
		 try {
		     out = new PrintStream(new FileOutputStream("playerdata.txt"));
		     pApp.println(playerData);
		 } catch (IOException e){
			 pApp.println("Could not save player data: " + e);
			 
		 } finally {
			 
			 
			 //// kill loading animation
		     if (out != null) out.close();
		 }
		 
	 }

	
	
	
	

} /// end class
