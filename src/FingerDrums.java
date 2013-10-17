import processing.core.*;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import controlP5.Println;
import processing.opengl.PGraphicsOpenGL;

public class FingerDrums {

	
	PApplet pApp;
	AppProfile theAppProfile;
	PlayerProfile thePlayerProfile;
	SoundControl theSoundControl;
	
	PImage theBground;
	String bgroundPath;
	//TimerClass seqTimer;
	
	//// HOT AREA
	float theX;
	float theY;
	float theWidth;
	float theHeight;
	// drums areas
	float kickX = 550;
	float kickY = 517;
	float kickWidth = 128;
	float kickHeight = 120;
	float snareX = 391;
	float snareY = 426;
	float snareWidth = 128;
	float snareHeight = 52;
	float hihatX = 227;
	float hihatY = 350;
	float hihatWidth = 137;
	float hihatHeight = 38;
	// sequencer
	private String[] patternz={"13231323","10201020","10201120"};
	boolean playMode = false;
	float tempoMs = 1000;
	int currentNote = 0;
	int currentPattern = 0;
	/// current hits
	int curHits = 0;
	
	// listeners
	protected EventListenerList listenerList = new EventListenerList();
	
	FingerDrums(){
		
		/// initialize all game object singletons ////
		
		/// app profile gives you game data 
		/// 'stage' properties, and the main class
		theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;
    	//// sound control
    	theSoundControl = theSoundControl.getInstance();
     	/// player profile gives you scoring
    	thePlayerProfile = thePlayerProfile.getInstance();
		
		
    	/// load the bground image
    	bgroundPath = "data/games/fingerdrums.png";
    	theBground = pApp.loadImage(bgroundPath);
    	
    	
    	///create the "hot" area for triggering
    	
    	theX = 500;
    	theY = 400;
    	theWidth = 100;
    	theHeight = 100;
  	  	// sequencer timer
  	  	//seqTimer = new TimerClass();
  	  	//seqTimer.start();
  	  	playMode = true;
	}
	
	public void checkHit(float tx, float ty, float tz){
		
		//// if a finger is over the "hot" area
		//// and there's fingers
		//// do a sound
		
		pApp.fill(0,0,255,65);
		
		pApp.ellipse(tx, ty,25, 25);
		
		if(theAppProfile.curNumFingers >0)
		{
			if ( playMode == false )
			{
				if ( tx > kickX && tx < kickX + kickWidth && ty > kickY && ty < kickY + kickWidth)
				{	
					theSoundControl.playKick();
				}
				if ( tx > snareX && tx < snareX + snareWidth && ty > snareY && ty < snareY + snareWidth)
				{	
					theSoundControl.playSnare();
				}
				if ( tx > hihatX && tx < hihatX + hihatWidth && ty > hihatY && ty < hihatY + hihatWidth)
				{	
					theSoundControl.playHihat();
				}
				
			}
			
			//// pApp.println("YOU HAVE HIT THE DRUM");
			//doImpactSounds();
			
			//// should put a timer here
			//// so it doesn't multi-fire
			curHits +=1;
			/// check for achievment
			/// looks for the achievment listing
			/// in the game info json file
			/// you can rename the achievement,
			/// change its description, change its icon
			if(curHits > 5){
				launchCheevo(null, "Can I Kick It?");
				
			}
			
		}

	}
	
	
	 void doImpactSounds(){
	   //// play a random sound
	   //// from the sound control class
	   int theRnd = (int)pApp.random(3);
	   /// pApp.println("wall hit: " + theRnd);
	   //theSoundControl.playLaserSounds(theRnd);
	   switch (theRnd) {
			case 0:
				theSoundControl.playKick();
				break;
			case 1:
				theSoundControl.playSnare();
				break;
			case 2:
				theSoundControl.playHihat();
				break;
			default:
				break;
			}
     	

	 }
	 public void secChanged( int currentSec )
	 {
		 currentNote = currentSec % 8;
		 if ( playMode == true )
			{
			 	pApp.println(patternz[currentPattern].substring(currentNote, currentNote+1));
				int currentPatternIndex = Integer.parseInt( patternz[currentPattern].substring(currentNote, currentNote+1) );
				switch (currentPatternIndex) {
				case 1:
					theSoundControl.playKick();
					break;
				case 2:
					theSoundControl.playSnare();
					break;
				case 3:
					theSoundControl.playHihat();
					break;

				default:
					// 0 : nothing
					break;
				}
			}
	 }
	 void display(){
		
		//// draw background
		pApp.image(theBground, 0, 0);
		
		
		// draw the "hot" area
		
		pApp.fill(255,0,0,165);
		//pApp.rect(theX,  theY,  theWidth,  theHeight);

		// kick
		pApp.ellipse(kickX, kickY, kickWidth, kickHeight);
		// snare
		pApp.ellipse(snareX, snareY, snareWidth, snareHeight);
		// hihat
		pApp.ellipse(hihatX, hihatY, hihatWidth, hihatHeight);
	}
	
	
	 ///////////////////////////
    ////// achievment listeners //////////
    ///////////////////////////
    public void addMyEventListener(MyEventListener listener) {
        listenerList.add(MyEventListener.class, listener);
      }
      public void removeMyEventListener(MyEventListener listener) {
        listenerList.remove(MyEventListener.class, listener);
      }
      void launchCheevo(MyEvent evt, String tCheev) {
    	  pApp.println("EVENT: " + tCheev);
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == MyEventListener.class) {
            ((MyEventListener) listeners[i+1]).myEventOccurred(evt, tCheev);
          }
        }
      }
      
} /// end class
