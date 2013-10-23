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
 
 //// player params
 ArrayList<String> ranktypes = new ArrayList();
 int rankID = 0;
 
 int gameID = 1;

 /// soundParams
 ArrayList<String> soundsStarWars = new ArrayList();
 ArrayList<String> soundsLasers = new ArrayList();
 ArrayList<String> soundsBasketball = new ArrayList();
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

   public void initApp(PApplet p){
	   pApp = p;
	   
	   pApp.println("SETTING APP");
	   
   }
   
   public void initData(){
	   
	   pApp.println("INITIALIZING APP");
	   
	   
	   /// add game mode types
	   gameMode.add("FINGERBALL");
	   gameMode.add("MULTIFEET");
	   gameMode.add("BREAKOUT");
	   gameMode.add("FEATHERWEIGHT");
	   gameMode.add("GRABBER");
	   gameMode.add("FINGERDRUMS");
	   

	   //// add rank types
	   ranktypes.add("BEGINNER");
	   ranktypes.add("INTERN");
	   ranktypes.add("APPRENTICE");
	   ranktypes.add("JOURNEYMAN");
	   ranktypes.add("CRAFTSMAN");
	   ranktypes.add("MASTER"); 
	   
	   //// add sounds ///////
	  
	   soundsLasers.add("sounds/lasrhit3.wav");
	   soundsLasers.add("sounds/lasrhit1.wav");
	   soundsLasers.add("sounds/laserhit_short1.wav");
	   soundsLasers.add("sounds/laserhit_short4.wav");
	   soundsStarWars.add("sounds/laserhit_short5.wav");


   }

}
