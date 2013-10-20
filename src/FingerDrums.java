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
	private String[] patternz={"10002000","13231323","10201020","10201120"};
	private int noteCount = 8; // size of the pattern
	private int[] notes;
	private int[] pattern;
	private boolean playMode = false;
	private boolean seqStarted = false;
	//private float tempoMs = 1000;
	private int currentNote = 0;
	private int currentPattern = 0;
	private int currentPatternNotesCount = 0;
	/// current hits
	private int currentHit = 0;
	private int fingerHit = 0;
	private boolean fingerReady = false;
	private boolean fadeReady = false;
	private boolean win = true;
	private int alphaDecrement = 2;
	private int kickAlpha = 0;
	private int snareAlpha = 0;
	private int hihatAlpha = 0;
	private int kickColor = 192;
	private int snareColor = 192;
	private int hihatColor = 192;
	
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
    	pattern = new int[noteCount];
  	  	playMode = true;
	}
	
	public void checkHit(float tx, float ty, float tz){
		
		//// if a finger is over the "hot" area
		//// and there's fingers
		//// do a sound
		
		pApp.fill(0,0,255,65);
		
		pApp.ellipse(tx, ty,25, 25);
		//pApp.println( "checkHit fingerReady:" + fingerReady );
		// avoid multiple hits
		if ( fingerReady )
		{			
			// wait for next hit after a while
			fingerReady = false;
			if( theAppProfile.curNumFingers > 0 )
			{
				if ( playMode == false )
				{
					fingerHit = 0;
					if ( tx > kickX && tx < kickX + kickWidth && ty > kickY && ty < kickY + kickWidth )
					{	
						pApp.println("kick, tx:" + tx + " ty:" + ty );
						
						fingerHit = 1;
					}
					if ( tx > snareX && tx < snareX + snareWidth && ty > snareY && ty < snareY + snareWidth )
					{	
						pApp.println( "snare, tx:" + tx + " ty:" + ty );
						fingerHit = 2;
					}
					if ( tx > hihatX && tx < hihatX + hihatWidth && ty > hihatY && ty < hihatY + hihatWidth )
					{	
						pApp.println( "hihat, tx:" + tx + " ty:" + ty );
						fingerHit = 3;
					}
					if ( fingerHit == 0 )
					{
						//nothing hit
						fingerReady = true;
					}
					else
					{
						hitDrum( fingerHit );
						pApp.println("currentHit:" + currentHit + " notes[currentHit]:" + notes[currentHit] + " fingerHit:" + fingerHit + " win:" + win );
						if ( notes[currentHit] == fingerHit )
						{
							pApp.println( " win:" + win );
							
						}
						else
						{
							win = false;
						}
						
						if ( currentHit >= currentPatternNotesCount - 1)
						{
							// reset 
							currentHit = 0;
							// check if won achievement
							if ( win )
							{
								launchCheevo(null, "Can I Kick It?");
							}
							else
							{
								pApp.println( "lost" );
								//launchCheevo(null, "Can I Kick It?");
							}
							// leapmode end
							seqStarted = false;
							playMode = true;
						}
						else
						{
							currentHit++;
						}
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
			pattern[i] = element;
			// get rid of silences
			if ( element > 0 )
			{
				notes[currentPatternNotesCount++] = element;
			}
		}
		if ( currentPattern < patternz.length-1 )
		{
			currentPattern++;
		}
		else
		{
			currentPattern = 0;
		}
		pApp.println("initPattern, currentPattern:" + currentPattern + " patternz.length:" + patternz.length );
	 }
	 private void hitDrum( int hit )
	 {
		 pApp.println("hitDrum:" + hit);
		 switch (hit) {
			case 1:
				// kick				
				theSoundControl.playKick();
				kickAlpha = 255;
				fingerReady = false;
				break;
			case 2:
				// snare
				theSoundControl.playSnare();
				snareAlpha = 255;
				fingerReady = false;
				break;
			case 3:
				// hihat
				theSoundControl.playHihat();
				hihatAlpha = 255;
				fingerReady = false;
				break;
			default:
				// 0 : nothing
				break;
			}
	 }
	 public void secChanged( int currentSec )
	 {
		currentNote = currentSec % noteCount;
		
		if ( playMode == true )
		{
			pApp.println("playmode started");
			// if we are on 1st beat and the sequencer has not started, we start playback
			if ( seqStarted == false && currentNote == 0 )
			{
				initPattern();
				seqStarted = true;				
			}
			if ( seqStarted == true )
			{		
			 	pApp.println("currentNote:" + currentNote + " pattern[currentNote]:"  +pattern[currentNote]);
				int currentPatternIndex = pattern[currentNote];
				if ( currentPatternIndex > 0 ) hitDrum(currentPatternIndex);
				// if it's the end of the pattern, we stop playback
				if ( currentNote == noteCount - 1 )
				{
					playMode = false;
					seqStarted = false;
					fingerReady = true;	
				}
				
			}
		}
		else
		{
			// playmode is done, now leapmotion game mode
			// we start recording finger hits
			if ( seqStarted == false )
			{
				pApp.println( "leapmotion game mode" );
				initLeap();
				seqStarted = true;				
			}
			else
			{
				//delay the finger hits
//				if ( fingerReady == false )
//				{
//					pApp.println( "fingerReady:" + fingerReady );
//					fingerReady = true;				
//				}
			}
		}
	 }
	 void display(){
		
		//// draw background
		pApp.image(theBground, 0, 0);

		//pApp.strokeWeight(0); // Eric, check if needs to be reset in main.java when leaving this game
		pApp.fill(255,0,0,165);
		fadeReady = true;
		// kick	
		if ( kickAlpha > alphaDecrement )
		{
			kickAlpha -= alphaDecrement;
			fadeReady = false;
		}
		
		pApp.fill( kickColor, 0, 0, kickAlpha );
		pApp.ellipse( kickX, kickY, kickWidth, kickHeight );		
		// snare
		if ( snareAlpha > alphaDecrement )
		{
			snareAlpha -= alphaDecrement;
			fadeReady = false;
		}
		pApp.fill( 0, 0, snareColor, snareAlpha);	
		pApp.ellipse( snareX, snareY, snareWidth, snareHeight );
		// hihat
		if ( hihatAlpha > alphaDecrement )
		{
			hihatAlpha -= alphaDecrement;
			fadeReady = false;
		}
		pApp.fill( 0, hihatColor, 0, hihatAlpha);		
		pApp.ellipse( hihatX, hihatY, hihatWidth, hihatHeight );
		// if animation finished we are ready for another hit
		if ( fadeReady ) fingerReady = true;
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
