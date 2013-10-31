import processing.core.*;
import ddf.minim.*;

import java.util.ArrayList;


class SoundControl extends PApplet{

	Minim minim;
	AudioSample plate;
	AudioSample rattle;
	AudioSample sheet;
	AudioSample snares;
	///
	AudioSample clash1;
	AudioSample clash2;
	AudioSample clash3;
	AudioSample clash4;
	AudioSample clash5;
	AudioSample clash6;
	
	AudioPlayer wall1;
	AudioPlayer wall2;
	AudioInput input;

	AudioSample kick;
	AudioSample snare;
	AudioSample hihat;
	AudioSample cowbell;
	
	/// star wars sounds
	AudioSample starWars1;
	AudioSample starWars2;
	AudioSample starWars3;
	AudioSample starWars4;
	AudioSample starWars5;
	AudioSample starWars6;
	AudioSample starWars7;
	AudioSample starWars8;
	
	/// basketball sounds
	AudioSample bBall1;
	AudioSample bBall2;
	AudioSample bBall3;
	AudioSample bBall4;
	AudioSample bBall5;
	AudioSample bBall6;
	AudioSample bBall7;
	AudioSample bBall8;
	
	/// breakout sounds
	
	
	
	//// featherweight sounds
	AudioSample featherGameStart;
	AudioSample featherSuccess;
	AudioSample featherRespawn;
	
	//// game state sounds
	
	AudioSample winLevel;
	AudioSample loseLevel;
	AudioSample newGame;
	
	ArrayList<AudioSample> StarWarsSounds = new ArrayList();
    ArrayList<AudioSample> SoundsLasers = new ArrayList();
    ArrayList<AudioSample> SoundsBasketball = new ArrayList();
    ArrayList<AudioSample> SoundsBreakout = new ArrayList();
    ArrayList<AudioSample> SoundsFeatherweight = new ArrayList();
    ArrayList<AudioSample> SoundtracksAll =  new ArrayList();
    ArrayList<AudioSample> SoundsGameState = new ArrayList();

	
	PApplet pApp;
	AppProfile theAppProfile;
	

	private static SoundControl instance = null;

	
	protected SoundControl() {
	      // Exists only to defeat instantiation.
		/// set up app profile
		theAppProfile = theAppProfile.getInstance();
		pApp = theAppProfile.pApp;
		
	    minim = new Minim(this);
	    plate = minim.loadSample("sounds/metalplate.wav", 512);
	    rattle = minim.loadSample("sounds/metalrattle.wav", 512);
	    sheet = minim.loadSample("sounds/metalsheet.wav", 512);
	    snares = minim.loadSample("sounds/metalsnares.wav", 512);
	    
	    kick = minim.loadSample("sounds/kick.wav", 512);
	    snare = minim.loadSample("sounds/snare.wav", 512);
	    hihat = minim.loadSample("sounds/hihat.wav", 512);
	    cowbell = minim.loadSample("sounds/cowbell.wav", 512);
	    
	    ///// add star wars sounds
	    
	    starWars1 = minim.loadSample("sounds/swing1.wav");
	    starWars2 = minim.loadSample("sounds/swing2.wav");
	    starWars3 = minim.loadSample("sounds/swing3.wav");
	    starWars4 = minim.loadSample("sounds/Swing01.wav");
	    starWars5 = minim.loadSample("sounds/Spin1.wav");
	    starWars6 = minim.loadSample("sounds/Spin2.wav");
	    
	    //// add basketball sounds
	    bBall1 = minim.loadSample("sounds/basketball_sounds/bball_1.wav");
	    bBall2 = minim.loadSample("sounds/basketball_sounds/bball_2.wav");
	    bBall3 = minim.loadSample("sounds/basketball_sounds/bball_3.wav");
	    bBall4 = minim.loadSample("sounds/basketball_sounds/bball_4.wav");
	    bBall5 = minim.loadSample("sounds/basketball_sounds/bball_5.wav");
	    
	    //// add breakout sounds
	    
	    /// add featherweight sounds
		featherGameStart = minim.loadSample("sounds/feather_sounds/Gliss Arpeggios 01.wav");
		featherRespawn = minim.loadSample("sounds/feather_sounds/Liquid Drops FX 02.wav");
		featherSuccess = minim.loadSample("sounds/feather_sounds/Pulsing Logo Accent 01.wav"); /// change this
	    
	    //// add game state sounds
	    winLevel = minim.loadSample("sounds/gamestate_sounds/Pulsing Logo Accent 01.wav");
		loseLevel = minim.loadSample("sounds/gamestate_sounds/Alarm.wav");
		newGame = minim.loadSample("sounds/gamestate_sounds/Synth Zap Accent 06.wav");
		

	
	    addSoundsToArray();
	}
	
	private void addSoundsToArray(){
		StarWarsSounds.add(starWars1);
		StarWarsSounds.add(starWars2);
		StarWarsSounds.add(starWars3);
		StarWarsSounds.add(starWars4);
		StarWarsSounds.add(starWars5);
		StarWarsSounds.add(starWars6);
		
		SoundsBasketball.add(bBall1);
		SoundsBasketball.add(bBall2);
		SoundsBasketball.add(bBall3);
		SoundsBasketball.add(bBall4);
		SoundsBasketball.add(bBall5);
		
		// add feather sounds
		
		SoundsFeatherweight.add(featherRespawn);
		SoundsFeatherweight.add(featherSuccess);
		SoundsFeatherweight.add(featherGameStart);
		
		// add game state sounds
		SoundsGameState.add(winLevel);
		SoundsGameState.add(loseLevel);
		SoundsGameState.add(newGame);
		
	}
	void playKick(){		
		kick.trigger();		
	}
	void playSnare(){		
		snare.trigger();		
	}
	void playHihat(){		
		hihat.trigger();		
	}
	void playCowbell(){		
		cowbell.trigger();		
	}
	void initSoundControl(){
	
		
	}

	public static SoundControl getInstance() {
	      if(instance == null) {
	         instance = new SoundControl();
	      }
	      return instance;
	}
	///// STOP ALL
	void stopAll(){
		plate.close();
		rattle.close();
		sheet.close();
		snares.close();
	
	}
	
	//// ACTIVATE SOUND
	void playStarWarsSound(int theID){
		
		try{
			AudioSample tSnd = StarWarsSounds.get(theID);
			tSnd.trigger();
			
			
		} catch (Exception e){
			pApp.println("error playing star wars sound: " + e);
		}

	}
	
	
	void playLaserSounds(int theID){
		String soundpath ="";
		try{
			
			
		} catch (Exception e){
			pApp.println("error loading lazer sound: " + soundpath);
		}
		
	}
	
	void playBasketballSound(int theID){
		try{
			AudioSample tSound = SoundsBasketball.get(theID);
			tSound.trigger();
			
		} catch (Exception e){
			pApp.println("error loading basketball sound: " + e);
		}
		
	}
	
	void playFeatherSounds(int theID){
		
		try{
			AudioSample tSound = SoundsFeatherweight.get(theID);
			tSound.trigger();
			
		} catch (Exception e){
			pApp.println("error loading feather sound: " + e);
		}
		
	}
	
	void playGameStateSounds(int theID){
		
		try{
			AudioSample tSound = SoundsGameState.get(theID);
			tSound.trigger();
			
		} catch (Exception e){
			pApp.println("error loading gamestate sound: " + e);
		}
		
	}


	///// ACTIVATE EACH
	void triggerClash3(){
		
		clash3.trigger();
	}
	void triggerClash4(){
		clash4.trigger();
	}
	void triggerClash5(){
		clash5.trigger();
	}
	
	void triggerPlate(){
		plate.trigger();

	}
	
	void triggerRattle(){
		rattle.trigger();
	}
	
	void triggerSheet(){
		  sheet.trigger();
	}
	
	void triggerSnares(){
		  snares.trigger();
	}
	
	void triggerStop(){
		minim.stop();
	}
	
	
	
	

}
