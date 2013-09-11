import java.util.ArrayList;

import processing.core.PApplet;


public class AppProfile {

	 private static AppProfile instance = null;
	 public static PApplet pApp;
	 
	 /// stage params
	 int theWidth = 1024;
	 int theHeight = 768;
	 /// color params
	 
	 int scoredata;
	 String rankdata = "noob";
	 String gametypedata = "SmashBlock";
	 
	 /// soundParams
	 ArrayList<String> soundsStarWars = new ArrayList();
	 public boolean isBlack;
	 
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

	   /*
	    clash1 = minim.loadSample("sounds/clash 01.wav", 512);
	    clash2 = minim.loadSample("sounds/Swing01.wav", 512);
	    clash3 = minim.loadSample("sounds/lasrhit3.wav", 512); 
	    clash4 = minim.loadSample("sounds/lasrhit1.wav", 512);
	    clash5 = minim.loadSample("sounds/Spin 1.wav", 512);
	    clash6 = minim.loadSample("sounds/Spin 2.wav", 512); 
	   */
	   

   }

}
