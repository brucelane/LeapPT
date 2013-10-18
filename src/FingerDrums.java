import processing.core.*;

import java.util.ArrayList;
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
	
	// drums areas
	private float kickX = 550;
	private float kickY = 517;
	private float kickWidth = 128;
	private float kickHeight = 120;
	private float snareX = 391;
	private float snareY = 426;
	private float snareWidth = 128;
	private float snareHeight = 52;
	private float hihatX = 227;
	private float hihatY = 350;
	private float hihatWidth = 137;
	private float hihatHeight = 38;
	// sequencer
	private String[] patternz={"13231323","10201020","10201120"};
	private int[] notes;
	private boolean playMode = false;
	private boolean seqStarted = false;
	private float tempoMs = 1000;
	private int currentNote = 0;
	private int currentPattern = 0;
	private int currentPatternNotesCount = 0;
	private int noteCount = 8;
	/// current hits
	private int currentHit = 0;
	private int fingerHit = 0;
	private boolean fingerReady = false;
	private boolean win = true;
	
	// listeners
	protected EventListenerList listenerList = new EventListenerList();
	
	FingerDrums()
	{
		
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

  	  	// sequencer 
    	notes = new int[noteCount];
  	  	playMode = true;
	}
	
	public void checkHit(float tx, float ty, float tz){
		
		//// if a finger is over the "hot" area
		//// and there's fingers
		//// do a sound
		
		pApp.fill(0,0,255,65);
		
		pApp.ellipse(tx, ty,25, 25);
		// avoid multiple hits
		if ( fingerReady )
		{
		
			if( theAppProfile.curNumFingers > 0 )
			{
				if ( playMode == false )
				{
					// wait for next hit after a while
					fingerReady = false;
					if ( tx > kickX && tx < kickX + kickWidth && ty > kickY && ty < kickY + kickWidth )
					{	
						fingerHit = 1;
					}
					if ( tx > snareX && tx < snareX + snareWidth && ty > snareY && ty < snareY + snareWidth )
					{	
						fingerHit = 2;
					}
					if ( tx > hihatX && tx < hihatX + hihatWidth && ty > hihatY && ty < hihatY + hihatWidth )
					{	
						fingerHit = 3;
					}
					hitDrum( fingerHit, 1 );
					if ( notes[currentHit] == fingerHit )
					{
						
					}
					else
					{
						win = false;
					}
					currentHit++;
					if ( currentHit > currentPatternNotesCount)
					{
						// check if won achievement
						if ( win )
						{
							launchCheevo(null, "Can I Kick It?");
						}
						// leapmode end
						playMode = true;
					}
				}
				
				//// pApp.println("YOU HAVE HIT THE DRUM");
				//doImpactSounds();
				
				//// should put a timer here
				//// so it doesn't multi-fire
				//curHits +=1;
				/// check for achievment
				/// looks for the achievment listing
				/// in the game info json file
				/// you can rename the achievement,
				/// change its description, change its icon
				//			if(curHits > 5){
				//				launchCheevo(null, "Can I Kick It?");
				//				
				//			}
			}
		}

	}
	 private void initLeap()
	 {	
		 fingerHit = 0;
		 win = true;
	 }
	 private void initPattern()
	 {
		 pApp.println("initPattern, noteCount:" + noteCount);
		// setup sequencer pattern
		currentPatternNotesCount = 0;
		for (int i = 0; i < noteCount; i++) {
			int element = Integer.parseInt( patternz[currentPattern].substring( i, i+1 ) );
			pApp.println("currentPatternNotesCount:" + currentPatternNotesCount + " element:" + element);
			// get rid of silences
			if ( element > 0 )
			{
				notes[currentPatternNotesCount++] = element;
			}
		}
	 }
	 private void hitDrum( int hit, int color )
	 {
		 pApp.println("hitDrum:" + hit);
		 switch (hit) {
			case 1:
				// kick
				pApp.fill(255,0,255,165);
				pApp.ellipse(kickX, kickY, kickWidth, kickHeight);
				theSoundControl.playKick();

				break;
			case 2:
				// snare
				pApp.fill(0,0,255,165);
				pApp.ellipse(snareX, snareY, snareWidth, snareHeight);
				theSoundControl.playSnare();
				break;
			case 3:
				// hihat
				pApp.fill(0,255,0,165);
				pApp.ellipse(hihatX, hihatY, hihatWidth, hihatHeight);
				theSoundControl.playHihat();
				break;
			default:
				// 0 : nothing
				break;
			}
	 }
	 public void secChanged( int currentSec )
	 {
		currentNote = currentSec % 8;
		pApp.println("playmode:" + playMode + " currentNote:" + currentNote);
		if ( playMode == true )
		{
			// if we are on 1st beat and the sequencer has not started, we start playback
			if ( seqStarted == false && currentNote == 0 )
			{
				initPattern();
				seqStarted = true;				
			}
			if ( seqStarted == true )
			{		
			 	pApp.println(notes[currentNote]);
				int currentPatternIndex = notes[currentNote];
				hitDrum(currentPatternIndex, 0);
				// if it's the end of the pattern, we stop playback
				if ( currentNote == 7 )
				{
					playMode = false;
					seqStarted = false;
				}
				
			}
		}
		else
		{
			// playmode is done, now leapmotion game mode
			// we start recording finger hits
			if ( seqStarted == false )
			{
				initLeap();
				seqStarted = true;				
			}
			else
			{
				if ( fingerReady == false )
				{
					
					fingerReady = true;				
				}
			}
		}
	 }
	 void display(){
		
		//// draw background
		pApp.image(theBground, 0, 0);
		
		
		// draw the "hot" area
		
		pApp.fill(255,0,0,165);
		// kick
		//pApp.ellipse(kickX, kickY, kickWidth, kickHeight);
		// snare
		//pApp.ellipse(snareX, snareY, snareWidth, snareHeight);
		// hihat
		//pApp.ellipse(hihatX, hihatY, hihatWidth, hihatHeight);
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
