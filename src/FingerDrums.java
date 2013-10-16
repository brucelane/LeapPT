import processing.core.*;

import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;

import processing.opengl.PGraphicsOpenGL;

public class FingerDrums {

	
	PApplet pApp;
	AppProfile theAppProfile;
	PlayerProfile thePlayerProfile;
	SoundControl theSoundControl;
	
	PImage theBground;
	String bgroundPath;
	
	//// HOT AREA
	float theX;
	float theY;
	float theWidth;
	float theHeight;

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
    	
    	theX = theAppProfile.theWidth/2;
    	theY = theAppProfile.theHeight/2;
    	theWidth = 100;
    	theHeight = 100;
		
	}
	
	public void checkHit(float tx, float ty, float tz){
		
		//// if a finger is over the "hot" area
		//// and there's fingers
		//// do a sound
		
		pApp.fill(0,0,255,65);
		
		pApp.ellipse(tx, ty,25, 25);
		
		if(theAppProfile.curNumFingers >0 && tx > theX && tx < theX + theWidth && ty > theY && ty < theY + theWidth){	
			//// pApp.println("YOU HAVE HIT THE DRUM");
			doImpactSounds();
			
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
	   int theRnd = (int)pApp.random(4);
	   /// pApp.println("wall hit: " + theRnd);
	   theSoundControl.playLaserSounds(theRnd);

	 }
	    
	void display(){
		
		//// draw background
		pApp.image(theBground, 0, 0);
		
		
		// draw the "hot" area
		
		pApp.fill(255,0,0,165);
		pApp.rect(theX,  theY,  theWidth,  theHeight);
		
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
