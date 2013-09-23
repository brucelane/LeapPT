import processing.core.*;
import ddf.minim.*;


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
	    /*
	    clash1 = minim.loadSample("sounds/clash01.wav", 512);
	    clash2 = minim.loadSample("sounds/Swing01.wav", 512);
	    clash3 = minim.loadSample("sounds/lasrhit3.wav", 512); 
	    	  
	    clash4 = minim.loadSample("sounds/lasrhit1.wav", 512);
	    clash5 = minim.loadSample("sounds/Spin 1.wav", 512);
	      */
	    try{
	    	 clash6 = minim.loadSample("sounds/lasrhit3.wav", 512); 
	  	   
	    } catch(Exception e){
	    	
	    	pApp.println(e);
	    }
	    

	    wall1 = minim.loadFile("sounds/5clash2.wav");
	     
	    
	    
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
			String soundpath = theAppProfile.soundsStarWars.get(theID);
			boolean isPlaying = wall1.isPlaying();
			/// println(theID + " " + soundpath);
			
			if(isPlaying){
				
			} else {
				wall1 = minim.loadFile(soundpath);
				wall1.play();
			}
			
			
		} catch (Exception e){
			pApp.println("error loading sound: " + e);
		}

	}
	
	
	void playLaserSounds(int theID){
		String soundpath ="";
		try{
			soundpath = theAppProfile.soundsLasers.get(theID);
			boolean isPlaying = wall1.isPlaying();
			/// println(theID + " " + soundpath);
			
			if(isPlaying){
				
			} else {
				wall1 = minim.loadFile(soundpath);
				wall1.play();
			}
			
			
		} catch (Exception e){
			pApp.println("error loading sound: " + soundpath);
		}
		
	}
	
	void playBasketballSound(int theID){
		try{
			String soundpath = theAppProfile.soundsBasketball.get(theID);
			boolean isPlaying = wall1.isPlaying();
			/// println(theID + " " + soundpath);
			
			if(isPlaying){
				
			} else {
				wall1 = minim.loadFile(soundpath);
				wall1.play();
			}
			
			
		} catch (Exception e){
			pApp.println("error loading sound: " + e);
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
