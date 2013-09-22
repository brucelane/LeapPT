import java.util.ArrayList;
import processing.core.PApplet;


public class AppProfile {

 private static AppProfile instance = null;
 public static PApplet pApp;
 
 /// input params
 int curNumFingers;
 
 /// stage params
 int theWidth = 1024;
 int theHeight = 768;
 /// color params
 
 int scoredata;
 String rankdata = "noob";

 /// soundParams
 ArrayList<String> soundsStarWars = new ArrayList();
 ArrayList<String> soundsBreakout = new ArrayList();
 
 /// game mode params
 ArrayList<String> gameMode = new ArrayList();
 
 protected AppProfile() {
      // Exists only to defeat instantiation.
   }
   public static AppProfile getInstance() {
      if(instance == null) {
         instance = new AppProfile();
      }
      return instance;
   }

   public void SetPApp(PApplet p){
	   pApp = p;
	   
	   
	   //// add sounds
	   //// clean this
	   soundsStarWars.add("sounds/laserhit_short1.wav");
	   soundsStarWars.add("sounds/laserhit_short2.wav");
	   soundsStarWars.add("sounds/laserhit_short3.wav");
	   soundsStarWars.add("sounds/laserhit_short4.wav");
	   soundsStarWars.add("sounds/laserhit_short5.wav");
	   
	   soundsStarWars.add("sounds/swing1.wav");
	   soundsStarWars.add("sounds/swing2.wav");
	   soundsStarWars.add("sounds/swing3.wav");
	   soundsStarWars.add("sounds/Swing01.wav");
	   
	   soundsStarWars.add("sounds/lasrhit3.wav");
	   soundsStarWars.add("sounds/lasrhit1.wav");
	   soundsStarWars.add("sounds/lasrhit3.wav");
	   soundsStarWars.add("sounds/Spin1.wav");
	   soundsStarWars.add("sounds/Spin2.wav");
	   
	   soundsStarWars.add("sounds/Honk_sad.mp3");

	   
	   /// add game mode types
	   gameMode.add("SmashBlocks");
	   gameMode.add("SpinBlock");
	   gameMode.add("TennisBlock");
	   gameMode.add("PaddleBlocks");
	   

   }

}
