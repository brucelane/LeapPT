import processing.core.*;

import javax.swing.event.EventListenerList;

import ijeoma.motion.*;
import ijeoma.motion.tween.*; 


public class FingerDrums {

	PApplet pApp;
	AppProfile theAppProfile;
	PlayerProfile thePlayerProfile;
	SoundControl theSoundControl;

	PImage theBground;
	PImage theDrums;
	PImage theRedPad;
	PImage theGreenPad;
	PImage theBluePad;
	PImage theYellowPad;
	PImage theTempo;
	String bgroundPath;
	//TimerClass seqTimer;
	/// game messages
	GameMessaging thePopup;

	// drums areas
	private float kickX = 510;
	private float kickY = 650;
	private float kickWidth = 310;
	private float kickHeight = 210;
	private float hihatX = 250;
	private float hihatY = 400;
	private float hihatWidth = 210;
	private float hihatHeight = 310;
	private float snareX = 800;
	private float snareY = 400;
	private float snareWidth = 210;
	private float snareHeight = 310;
	private float cowbellX = 510;
	private float cowbellY = 250;
	private float cowbellWidth = 310;
	private float cowbellHeight = 210;
	// sequencer
	private String[] patternz={"10203040","10101010","10002000","10201020","10201120","13231323"};
	private int noteCount = 8; // size of the pattern
	private int[] notes;
	private int[] pattern;
	private boolean playMode = false;
	private boolean seqStarted = false;
	//private float tempoMs = 1000;
	private int currentNote = 0;
	private int currentPatternNotesCount = 0;
	/// current hits
	private int currentHit = 0;
	private int fingerHit = 0;
	private boolean fingerReady = false;
	private boolean fadeReady = false;
	private boolean win = true;
	private int alphaDecrement = 10;
	private int kickAlpha = 0;
	private int snareAlpha = 0;
	private int cowbellAlpha = 0;
	private int tempoAlpha = 0;
	private int hihatAlpha = 0;
	private int curLevel = 0;
	private int rotationDuration = 50;
	private int secondsPlayed = 0;
	private int secondsTimeout = 10;
	private final float PI4 = (float) Math.PI / 4; // 0.76f
	private final float HALF_PI = (float) Math.PI / 2; // 1.57f
	private final float TWO_PI = (float) Math.PI * 2; // 6.28f
	private boolean debugging = true;
	private String debugMsg = "debug mode";
	/// need to turn this on and off from main
	public boolean gamePaused = true;
	private int targetAngle = 8;

	// tween
	PFont font;

	Sequence ts;
	Tween t;

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
		thePopup = thePopup.getInstance();

		/// load the bground image
		bgroundPath = "data/backgrounds/fingerdrums_bground.png";
		theBground = pApp.loadImage(bgroundPath);
		theDrums = pApp.loadImage("data/games/fingerdrums_drumpads.png");
		theRedPad = pApp.loadImage("data/games/fingerdrums_redpad.png");
		theGreenPad = pApp.loadImage("data/games/fingerdrums_greenpad.png");
		theBluePad = pApp.loadImage("data/games/fingerdrums_bluepad.png");
		theYellowPad = pApp.loadImage("data/games/fingerdrums_yellowpad.png");
		theTempo = pApp.loadImage("data/games/tempo.png");

		// sequencer 
		notes = new int[noteCount];
		pattern = new int[noteCount];
		playMode = true;

		// tween
		Motion.setup(pApp);
		t = new Tween(0, 1, rotationDuration);
		/// show first message
	}

	public void startNewGame()
	{
		thePopup.initMessage(0, 0, "WELCOME TO FINGERDRUMS!", "Follow along with the drum solo! Watch the flashing colors and listen for the tones...\n\nThen it's your turn! Repeat the pattern by moving your hand towards the Leap controller.\n\nFirst, let's start with 4 kick-drums", null,255, 255);
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
					if ( tx > cowbellX && tx < cowbellX + cowbellWidth && ty > cowbellY && ty < cowbellY + cowbellWidth )
					{        
						pApp.println( "cowbell, tx:" + tx + " ty:" + ty );
						fingerHit = 4;
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
							thePlayerProfile.GameStats.get(theAppProfile.gameID).curScore += 12;
						}
						else
						{
							win = false;
						}
						if ( currentHit >= currentPatternNotesCount - 1)
						{
							endLevel();
						}
						else
						{
							currentHit++;
						}
					}
				}
			}
		}
	}
	////// THIS IS CALLED WHEN LEVEL FINISHING PARAMETERS ARE MET ////////
	private void endLevel()
	{
		String popupTitle;
		String popupNextLevelMessage;
		String popupMessage;
		// reset 
		currentHit = 0;
		// check if won achievement
		if ( win )
		{
			popupTitle = "YOU WIN! LEVEL COMPLETE!";
			thePlayerProfile.GameStats.get(theAppProfile.gameID).curScore += 142;
			launchCheevo(null, "Can I Kick It?");
		}
		else
		{
			popupTitle = "YOU LOSE...";
			pApp.println( "lost" );
		}

		// leapmode end
		gamePaused = true;
		seqStarted = false;
		playMode = true;

		curLevel++;        
		if ( curLevel > patternz.length - 1) curLevel = 0;
		switch (curLevel)
		{
		case 1:
			popupMessage = "LEVEL 2";
			popupNextLevelMessage = "Listen to the drums part.\n\nMeet the snare drum.";
			break;
		case 2:
			popupMessage = "LEVEL 3";
			popupNextLevelMessage = "Listen to the drums part.";
			break;

		default:
			popupMessage = "WELCOME TO FINGERDRUMS!";
			popupNextLevelMessage = "Listen to the drums part.\n\nThen try do play the same pattern!";
			break;                                                    
		} // endswitch
		thePopup.initMessage(0, 0, popupTitle, popupMessage + "\n" + popupNextLevelMessage, null, 255, 255);                                                            

	}
	//////// THIS IS CALLED FROM THE DIALOG CLOSE BUTTON
	//////// YOU NEVER HIT THIS UNLESS YOU"RE STARTING A NEW LEVEL
	public void doNextLevel()
	{            
		gamePaused = false;

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
		for (int i = 0; i < noteCount; i++) 
		{
			int element = Integer.parseInt( patternz[curLevel].substring( i, i+1 ) );
			pApp.println("currentPatternNotesCount:" + currentPatternNotesCount + " element:" + element);
			pattern[i] = element;
			// get rid of silences
			if ( element > 0 )
			{
				notes[currentPatternNotesCount++] = element;
			}
		}
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
		case 4:
			// cowbell
			theSoundControl.playCowbell();
			cowbellAlpha = 255;
			fingerReady = false;
			break;
		default:
			// 0 : nothing
			break;
		}
	}
	public void resetTween()
	{
		t.stop();
		t = new Tween( 0, 0, 1 );
		t.play();
	}
	public void secChanged( int currentSec )
	{
		if ( !gamePaused )
		{
			// fixes the start from 0 thingy currentNote = currentSec % noteCount;
			tempoAlpha = 255;
			if ( playMode == true )
			{
				// close message
				// if we are on 1st beat and the sequencer has not started, we start playback
				if ( seqStarted == false && currentNote == 0 )
				{
					// reset tween
					resetTween();
					
					initPattern();
					seqStarted = true;  
					secondsPlayed = 0;
				}
				if ( seqStarted == true )
				{      
					t.stop();
					if ( currentNote <  noteCount - 1 )
					{							
						pApp.println( "targetAngle:" + targetAngle + " pattern[currentNote+1]" + pattern[currentNote+1] + " pattern[currentNote]" + pattern[currentNote] );
						if ( pattern[currentNote+1] > 0 && pattern[currentNote+1] != targetAngle ) 
						{
							targetAngle = pattern[currentNote+1];
							t = new Tween( 0, 1, rotationDuration );
							t.play();
						}
					}						
					int currentDrum = pattern[currentNote];
					if ( currentDrum > 0 ) 
					{
						hitDrum(currentDrum);
					}
					
					// if it's the end of the pattern, we stop playback
					if ( currentNote == noteCount - 1 )
					{
						playMode = false;
						seqStarted = false;
						fingerReady = true;        
						gamePaused = true;        
						thePopup.initMessage(0, 0, "YOUR TURN !", "Play the drums part!", null, 255, 255);
					}
				}
				if ( currentNote <  noteCount - 1 )
				{
					currentNote++;
				}
				else
				{
					currentNote = 0;
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
			}
		}
		else
		{
			// game is paused, start from 0
			currentNote = 0;

			if ( !t.isPlaying() && playMode )
			{
				// small rotation while waiting
				t = new Tween( 0, 1, rotationDuration*5 );
				targetAngle = 4;
				t.repeat(100);
				t.play();
			}
		}
	}
	void display()
	{

		//// draw background
		pApp.image(theBground, 0, 0);

		// place the drums image in the center
		pApp.pushMatrix();
		pApp.translate(theAppProfile.theWidth/2,  theAppProfile.theHeight/2);// no z
		pApp.rotateZ( (t.getPosition() * HALF_PI * targetAngle) + HALF_PI/2); //(in radians, values from 0 to TWO_PI)
		pApp.image(theDrums, -theDrums.width/2, -theDrums.height/2);
		if(gamePaused == false)
		{
			fadeReady = true;
			pApp.fill(255,0,0,165);
			// kick        
			if ( kickAlpha > alphaDecrement )
			{
				kickAlpha -= alphaDecrement;
				fadeReady = false;
				pApp.image(theGreenPad, -theDrums.width/2, -theDrums.height/2);
			}

			// snare
			if ( snareAlpha > alphaDecrement )
			{
				snareAlpha -= alphaDecrement;
				fadeReady = false;
				pApp.image(theYellowPad, -theDrums.width/2, -theDrums.height/2);
			}

			// hihat
			if ( hihatAlpha > alphaDecrement )
			{
				hihatAlpha -= alphaDecrement;
				fadeReady = false;
				pApp.image(theBluePad, -theDrums.width/2, -theDrums.height/2);
			}

			// cowbell
			if ( cowbellAlpha > alphaDecrement )
			{
				cowbellAlpha -= alphaDecrement;
				fadeReady = false;
				pApp.image(theRedPad, -theDrums.width/2, -theDrums.height/2);
			}

			// tempo
			if ( tempoAlpha > alphaDecrement )
			{
				tempoAlpha -= alphaDecrement;
				pApp.image(theTempo, -theDrums.width/2, -theDrums.height/2);
			}
			// if animation finished we are ready for another hit
			if ( fadeReady ) fingerReady = true;
		}
		pApp.popMatrix();

		if(thePopup.showingMessage == true)
		{
			thePopup.drawMessage();
		}
		if ( debugging )
		{
			debugMsg = "targetAngle:" + targetAngle + " pattern[currentNote]:" + pattern[currentNote] + " gamePaused:" + gamePaused + " playMode:" + playMode + " t.getPosition():" + t.getPosition();
			pApp.text(debugMsg,  20, 600, 1000, 100);
		}
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