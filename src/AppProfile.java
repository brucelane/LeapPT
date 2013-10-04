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
 
 int gameID = 0;

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
	   //// add sounds
	   //// clean this
	   /*
	   soundsStarWars.add("sounds/laserhit_short1.wav");
	   soundsStarWars.add("sounds/laserhit_short2.wav");
	   soundsStarWars.add("sounds/laserhit_short3.wav");
	   soundsStarWars.add("sounds/laserhit_short4.wav");
	   soundsStarWars.add("sounds/laserhit_short5.wav");
	   */
	   
	   soundsStarWars.add("sounds/swing1.wav");
	   soundsStarWars.add("sounds/swing2.wav");
	   soundsStarWars.add("sounds/swing3.wav");
	   soundsStarWars.add("sounds/Swing01.wav");
	   soundsStarWars.add("sounds/Spin1.wav");
	   soundsStarWars.add("sounds/Spin2.wav");
	   soundsStarWars.add("sounds/Honk_sad.mp3");
	   
	   
	   soundsLasers.add("sounds/lasrhit3.wav");
	   soundsLasers.add("sounds/lasrhit1.wav");
	   soundsLasers.add("sounds/laserhit_short1.wav");
	   soundsLasers.add("sounds/laserhit_short4.wav");
	   soundsStarWars.add("sounds/laserhit_short5.wav");
	   
	   soundsBasketball.add("sounds/basketball_sounds/bball_0.wav");
	   soundsBasketball.add("sounds/basketball_sounds/bball_1.wav");
	   soundsBasketball.add("sounds/basketball_sounds/bball_2.wav");
	   soundsBasketball.add("sounds/basketball_sounds/bball_3.wav");
	   soundsBasketball.add("sounds/basketball_sounds/bball_4.wav");
	   soundsBasketball.add("sounds/basketball_sounds/bball_5.wav");
	   
	   /// add game mode types
	   gameMode.add("FINGERBALL");
	   gameMode.add("MULTIFEET");
	   gameMode.add("BREAKOUT");
	   gameMode.add("BABYONFIRE");
	   
	   //// add rank types
	   ranktypes.add("BEGINNER");
	   ranktypes.add("INTERN");
	   ranktypes.add("APPRENTICE");
	   ranktypes.add("JOURNEYMAN");
	   ranktypes.add("CRAFTSMAN");
	   ranktypes.add("MASTER"); 

   }

}
