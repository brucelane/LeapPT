import processing.core.*;
import processing.opengl.PGraphicsOpenGL;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Map;

import javax.swing.event.EventListenerList;
//// import java.util.Vector;


/// leap motion
import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.KeyTapGesture;
import com.leapmotion.leap.ScreenTapGesture;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Tool;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.Gesture.State;
// import com.leapmotion.leap.processing.LeapMotion;  //// removed this hoping it would fix app export problems
/// import com.leapmotion.leap.processing.LeapMotion;

import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlFont;
import controlP5.ControlP5;
/// minim audio
import ddf.minim.AudioSample;
import ddf.minim.Minim;


public class VisualTouchTherapy extends PApplet{
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "VisualTouchTherapy" });
	  }
	/// leap
	LeapMotion leapMotion;
	// controller and listeners
    Controller controller = new Controller();
	/// SampleListener listener = new SampleListener();

	
	/// finger arrays
	ConcurrentMap<Integer, Vector> fingerPositions;
	ConcurrentMap<Integer, Vector> toolPositions;
	ConcurrentMap<Integer, Integer> fingerColors;
	ConcurrentMap<Integer, Integer> toolColors;

	int curNumFingers;
	
	// UI display images ///////
	PImage controlPanel;
	PImage cheevoBadge;
	PImage cheevoBground;
	PImage footerBground;
	PImage mainBground;
	PImage pauseIcon;
	
	/// button images
	PImage b_MainMenu;
	PImage b_MainMenuR;
	
	PImage b_PlayPause;
	PImage b_PlayPauseR;
	
	String b_ppPath = "data/butt_playPause.png";
	String b_ppPathR = "data/butt_playPauseR.png";
	
	String b_mmPath = "data/butt_mainMenu.png";
	String b_mmPathR = "data/butt_mainMenuR.png";
	
	// Top Level Nav
	Button playPauseButton;
	Button menuButton;
	
	String chevBgroundPath = "data/achievement_bground.png";
	String footerBgPath = "data/footer_bground.png";
	///// control and images
	String panelPath = "data/nav_bground_2.png";
	
	 //// interface elements
	ControlP5 cp5;
	ControlP5 ControlEvent;
	ControlFont topNavPFont;
	
	String theScore = "0";
	String theTime = "0";
	String theRank = "";
	String theGameType = "null";
	
	/// game state data
	boolean isPaused = false;
	boolean isMenuShowing = false;

	///// fonts
	PFont ScoreFont = createFont("Gotham Rounded",22, true); /// normal fonts
	PFont pfont = createFont("Gotham Rounded",10, true); // use true/false for smooth/no-smooth for Control fonts
	PFont mainNavFont = createFont("Gotham Rounded",  22, true);

	/// font colors
	int blueNav;
	int whiteNav;
	int blueNavTransp;
	int whiteNavTransp;
	
	int numFingers = 0;

	static float LEAP_WIDTH = 200.0f; // in mm
	static float LEAP_HEIGHT = 700.0f; // in mm
	
	int tWidth = 1024;
	int tHeight = 768;

	float theX = 0;
	float theY = 0;
	float theZ = 0;
	
	// margins and spacing
	int topMargin = 20;

	// app profile
	AppProfile theAppProfile;
	/// player profile
	PlayerProfile thePlayerProfile;

	Messaging theMessaging;

	//// GESTURE TRACKING
	//// Swipe id: 123, STATE_STOP, position: (-98.4066, 175.594, 245.193), direction: (-0.286242, -0.268394, 0.919799), speed: 2278.775
	//// ConcurrentMap<Integer, String, Vector, Float> gestureType; 
	GestureProfile theGesture;
	
	/// arrays
	ArrayList<Mover> movers;
	ArrayList<Bouncer> bouncers;
	ArrayList<Spinner> spinners;
	ArrayList<Breakout> breakouts;
	ArrayList<Feather> feathers;
	ArrayList<Lifter> lifters;
	
	/// game objects //
	boolean gestureCheck = false;
	float gravWeight = .5f;  //// can we add this to difficulty level?
	
	/// achievment display
	boolean showCheevo = false;
	String cheevoName = "";
	String cheevoDesc = "";
	String cheevoPath = "";

	/// grabber
	Mover theMover;
	Attractor theAttractor;
	int totalMovers = 1;
	
	/// basketball
	Repulsor theRepulsor;
	Bouncer theBouncer;
	int totalBouncers = 1;
	
	/// multifeet
	Shaker theShaker;
	Spinner theSpinner;
	BackgroundTiles theBground;
	int totalSpinners = 1;
	
	//// breakout
	Breakout theBreakout;
	Paddle thePaddle;
	int totalBreakouts = 1;
	
	/// featherlight
	Lifter theLifter; // Repulsor theRepulsor;
	Feather theFeather; //Bouncer theBouncer;
	int totalFeathers = 1;
	int totalLifters = 1;
	
	/// fingerdrums
	
	FingerDrums theFingerDrums;
	int currentSec;
	
	TimerClass theTimer;
	
	float curMarker; // marker for particular time
	
	MyEventListener listener;

	public void setup(){	
	  size(1024,768, OPENGL);
	  // size(tWidth,tHeight, OPENGL);
	  colorMode(RGB);
	  smooth();
	  
	  /*
	  
	  textMode(SHAPE);
	  */
	  /// set up app profile
	  theAppProfile = theAppProfile.getInstance();
	  theAppProfile.initApp(this);
	  theAppProfile.initData();
	  //
	  theGesture = theGesture.getInstance();
	  // init player profile
	  thePlayerProfile = thePlayerProfile.getInstance();
	  // message box
	  theMessaging = new Messaging();
	  //
	  movers = new ArrayList();
	  bouncers = new ArrayList();
	  spinners = new ArrayList();
	  breakouts = new ArrayList();
	  lifters = new ArrayList();
	  feathers = new ArrayList();
	  //
	  //
	  theTimer = new TimerClass();
	  
	  tWidth = theAppProfile.theWidth;
	  tHeight = theAppProfile.theHeight;
	  // size(16*50, 9*50);
	  //  size(800,600, OPENGL);

	  frameRate(60);
	  ellipseMode(CENTER);

	  leapMotion = new LeapMotion(this);
	  fingerColors = new ConcurrentHashMap<Integer, Integer>();
	  toolColors = new ConcurrentHashMap<Integer, Integer>();
	  fingerPositions = new ConcurrentHashMap<Integer, Vector>();
	  toolPositions = new ConcurrentHashMap<Integer, Vector>();
	  
      controller.enableGesture(Gesture.Type.TYPE_SWIPE);
      controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
      controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
      controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	  
	  /// smooth();
	  
	  // movers
	  spawnMovers();
	  // bouncers
	  spawnBouncers();
	  // spinners
	  spawnSpinners();
  	  //
	  spawnBreakouts();
	  //
	  spawnFeathers();
	  
	  theBground = new BackgroundTiles(0,0,0);
	  theAttractor = new Attractor(gravWeight);
	  theRepulsor = new Repulsor(gravWeight);
	  theShaker = new Shaker(0);
	  thePaddle = new Paddle(gravWeight);
	  theLifter = new Lifter(gravWeight);
	  
	  //// FINGER DRUMS ////
	  theFingerDrums = new FingerDrums();
	  
	  /// add achievment listener 
	  theFingerDrums.addMyEventListener(new MyEventListener() {
	      public void myEventOccurred(MyEvent evt, String tCheev) {
	          /// System.out.println("fired");
	          initAchievement(tCheev);
	        }
	  });
	  
	  ///////////////////////////
	// add achievment listeners
	  ///////////////////////////
	  breakouts.get(0).addMyEventListener(new MyEventListener() {
      public void myEventOccurred(MyEvent evt, String tCheev) {
          System.out.println("fired" + tCheev);
          initAchievement(tCheev);
        }
      });
	  
	  /// add to the first and only spinner we're instantiating
	  spinners.get(0).addMyEventListener(new MyEventListener() {
      public void myEventOccurred(MyEvent evt, String tCheev) {
          /// System.out.println("fired");
          initAchievement(tCheev);
        }
      });
	  
	  feathers.get(0).addMyEventListener(new MyEventListener() {
	      public void myEventOccurred(MyEvent evt, String tCheev) {
	          /// System.out.println("fired");
	          initAchievement(tCheev);
	        }
	  });
  

	  ///  GUI assets /////////////////////
	  cp5 = new ControlP5(this);
	  
	  controlPanel = loadImage(panelPath);
	  cheevoBground = loadImage(chevBgroundPath);
	  footerBground = loadImage(footerBgPath);
	  mainBground = loadImage("data/backgrounds/bground_main.png");
	  pauseIcon = loadImage("data/paused_button.png");
	  // button assets
	  b_MainMenu = loadImage(b_mmPath);
	  b_MainMenuR = loadImage(b_mmPathR);
		
	  b_PlayPause = loadImage(b_ppPath);
	  b_PlayPauseR = loadImage(b_ppPathR);

		// Top Level Nav
	  
	  topNavPFont = new ControlFont(mainNavFont, 241);
	  //
	  blueNav = color(0,123,234);
	  whiteNav = color(255,255,255);
	  blueNavTransp = color(0,123,234,35);
	  whiteNavTransp = color(255,255,255,35);


	  /// make game menu be the init 
	  
	  doMenu();
	 
	  initGUI();

	}
	
	//// listeners


	public void draw(){
	  ////// make a nice background ///
	  background(0,0,0,0);
	  //fill(0,0,0);
	  // rect(0,0,theAppProfile.theWidth, theAppProfile.theHeight);
	  
	  
	  if(!isPaused){
		  // 
		  renderGame();
		  // 
	  } else {
		  // draw the game
		  renderGame();
		  theTimer.running = false;
		  /// are we showing a menu
		  /// if so, show it
		  if(isMenuShowing){
			  image(mainBground, 0,0);
			  theMessaging.drawMessageBox(); 
		
		  /// otherwise show the pause icon
		  } else {
			  image(pauseIcon, theAppProfile.theWidth/2 - pauseIcon.width/2, theAppProfile.theHeight/2 - pauseIcon.height/2);
			  
		  }
	  }
	  /// 
	  
	  doGUI();

	  //// 
	}
	
	
	///////////////////////////////////////
	///// GAME STATE CONTROL /////////////
	/////////////////////////////////////

	public void renderGame(){

		switch (theAppProfile.gameID){
		
		case 0:
			doRepulsor();
			drawBouncers();
			break;
			
		case 1:
			gestureCheck = true;
			doShaker();
			drawSpinners();
			break;
			
		case 2:
			doPaddle();
			drawBreakouts();
			break;
			
		case 3:

			drawFeather();
			doLifters();

			break;
			
		case 4:
			doAttractor();
			drawMovers();
			break;
		
		case 5:
			
			doFingerDrums();
			
		case 6:
			
			break;
		}
		
		if(showCheevo){
			
			showAchievment();
		}
		
		
	}
	
	///// START AND STOP GAMES /////////////
	public void startNewGame(int tId){
		
		thePlayerProfile.GameStats.get(theAppProfile.gameID).timeSpent = theTimer.getElapsedTime();
	    theTimer.stop();
	    theTime = "";
	    
		try{ 
			gestureCheck = false;
		} catch (Exception e){
			println("Can't kill listeners");
		}

		theAppProfile.gameID = tId;
		theTimer.start();
		
		switch (theAppProfile.gameID){
			case 0:
			
				break;
			
			case 1:
				
				break;
				
			case 2:
				breakouts.get(0).startNewGame();
				break;
				
			case 3: /// featherweight
				
				feathers.get(0).startNewGame();
				break;
				
			case 4:
				
				break;
				
			case 5:
				
				theFingerDrums.startNewGame();
				break;
				
				
			default:
				
				break;
			
		
		
		
		}
		
	}


	public void doNextGame(){
		//// 

	    thePlayerProfile.GameStats.get(theAppProfile.gameID).timeSpent = theTimer.getElapsedTime();
	    theTimer.stop();
	    theTime = "";
		
		/// should reset game and score
		try{ 
			gestureCheck = false;
		} catch (Exception e){
			println("Can't kill listeners");
		}
		
		if(theAppProfile.gameID >= theAppProfile.gameMode.size()-1){
			theAppProfile.gameID = 0;
		} else {
			theAppProfile.gameID ++;
		}
		
	    theTimer.start();
		
	}
	
	public void doPrevGame(){
		
		thePlayerProfile.GameStats.get(theAppProfile.gameID).timeSpent = theTimer.getElapsedTime();
	    theTimer.stop();
	    theTime = "";
	    
		try{ 
			gestureCheck = false;
		} catch (Exception e){
			println("Can't kill listeners");
		}
		
		if(theAppProfile.gameID <= 0){
			theAppProfile.gameID = theAppProfile.gameMode.size()-1;
		} else {
			theAppProfile.gameID --;
		}
		
		theTimer.start();
		
	}
	
	
	
    /////////////////////////////////////
    ////////// GAME MECHANICS BY TYPE ///
    /////////////////////////////////////
	
	
	///// ATTRACTORS /////////////////
	/// spawn movers
	void spawnMovers(){
	    // generate movers
	    movers.clear();
	    for (int i=0; i< totalMovers; i++){
	        // // println("ADDING MOVER: " + i);
	        // movers[i] = new Mover(random(0.1,5),0,0);
	        movers.add(new Mover(random(1.1f,5),0,0f));  
	    } 
	}
	
	public void doAttractor(){
	 ////// do the LEAP //////////
	  for (Map.Entry entry : fingerPositions.entrySet()){
		  
	    Integer fingerId = (Integer) entry.getKey();
	    Vector position = (Vector) entry.getValue();

	    // show finger colors
	    // fill(fingerColors.get(fingerId), 65);
	    stroke(255);
	    strokeWeight(1);
	    
	    float tSize = map(position.getZ(), -100.0f, 100.0f, 0.0f ,20.0f);
	    pushMatrix();
	    
	    //// move the ellipse in the z index
	    translate(0,0,300);
	    ellipse(leapToScreenX(position.getX()), leapToScreenY(position.getY()), tSize/2, tSize/2);
	    popMatrix();
	    
	    /// only activate if fingers are showing

		theAttractor.update(leapToScreenX(position.getX()), leapToScreenY(position.getY()), position.getZ());
	    theAttractor.display();
	    
	  }
	}

		
	public void drawMovers(){
	    for (int i=0; i< movers.size(); i++){
	        Mover dMover = movers.get(i);
	        /// movers[i].applyForce(gravity);
	        PVector f = theAttractor.attract(dMover);
	        dMover.applyForce(f);
	        dMover.update();
	        dMover.checkEdges();
	        dMover.display();
	    }
	}
	
	
	///////// FINGER BLOCKS ////////////////
	
	void spawnBreakouts(){
	    // generate movers
	    breakouts.clear();
	    for (int i=0; i< totalBreakouts; i++){
	        // // println("ADDING MOVER: " + i);
	        // movers[i] = new Mover(random(0.1,5),0,0);
	        breakouts.add(new Breakout(random(1.1f,5),0,0f));  
	    } 
	}
		
	
	public void doPaddle(){
		 ////// do the LEAP //////////
		  for (Map.Entry entry : fingerPositions.entrySet()){
			  
		    Integer fingerId = (Integer) entry.getKey();
		    Vector position = (Vector) entry.getValue();
		    thePaddle.update(leapToScreenX(position.getX()), leapToScreenY(position.getY()), 300);

		  }
		  
		  thePaddle.display();
		}

	public void drawBreakouts(){
	    for (int i=0; i< bouncers.size(); i++){
	        Breakout dBreakout = breakouts.get(i);
	        /// movers[i].applyForce(gravity);
	        PVector f = thePaddle.repulse(dBreakout);
	        dBreakout.applyForce(f);
	        dBreakout.update();
	        dBreakout.checkEdges();
	        dBreakout.display();
	    }
	}
	
	
	//// SPINNERS //////////////
	public void doShaker(){

		//// have you made a gesture?
        if(theGesture.state == "STATE_UPDATE"){
			
			/// ADD SWIPE VECTORS TO CHECKERBOARD AND SPINNER
			try{
				theBground.doImpact(map(theGesture.position.x * -1, 0,100,0, 0.1f), map(theGesture.position.y * 1, 0,100,0, 0.1f), map(theGesture.position.z * -1, 0,100,0, 0.01f));
				
				/// println(map(theGesture.position.x * -1, 0,100,0, 0.9f) + " y: " +  map(theGesture.position.y * -1, 0,100,0, 0.9f));
				for (int i=0; i< spinners.size(); i++){
			        Spinner dSpinner = spinners.get(i);
			        dSpinner.doImpact(map(theGesture.position.x * -1, 0,100,0, 0.1f), map(theGesture.position.y * 1, 0,100,0, 0.1f), map(theGesture.position.z * -1, 0,100,0, 0.01f));
				}
				
			} catch (Exception e){
				
				println("ERROR ON GESTURE: " + e);
			}
			
		} else if(theGesture.state == "STATE_STOP"){
			
			
		}
        
        try{
        	theShaker.update(theGesture.position.x, theGesture.position.y, theGesture.position.z);
        } catch (Exception e) {
        	/// println("No gesute data : " + e);
        	theShaker.update(0, 0, 0);
        	
        }
		theShaker.display();
        theBground.update();
        theBground.display();
		
	}
	/// spawn spinners
	void spawnSpinners(){
	    // generate spinners
	    spinners.clear();
	    for (int i=0; i< totalSpinners; i++){
	        // // println("ADDING MOVER: " + i);
	        // movers[i] = new Mover(random(0.1,5),0,0);
	    	spinners.add(new Spinner(random(1.1f,5),0,0f));  
	    } 
	}
		
	public void drawSpinners(){
	    for (int i=0; i< spinners.size(); i++){
	        Spinner dSpinner = spinners.get(i);
	        /// movers[i].applyForce(gravity);
	        /*
	        PVector f = theRepulsor.repulse(dBouncer);
	        dBouncer.applyForce(f);
	        dBouncer.update();
	        dBouncer.checkEdges();
	        */
	        dSpinner.update();
	        dSpinner.display();
	    }
	}
	
	
	///////// BOUNCER ////////////////
		
	void spawnBouncers(){
	    // generate movers
	    bouncers.clear();
	    for (int i=0; i< totalBouncers; i++){
	        bouncers.add(new Bouncer(random(1.1f,5),0,0f));  
	    } 
	}
		
	
	public void doRepulsor(){
		
		 theRepulsor.doBground();
		 ////// do the LEAP //////////
		  for (Map.Entry entry : fingerPositions.entrySet()){
			  
		    Integer fingerId = (Integer) entry.getKey();
		    Vector position = (Vector) entry.getValue();
	
		    // show finger colors
		    // fill(fingerColors.get(fingerId), 65);
		    stroke(255);
		    strokeWeight(1);
		    
		    float tSize = map(position.getZ(), -100.0f, 100.0f, 0.0f ,20.0f);
		   ////  pushMatrix();
		    
		    //// move the ellipse in the z index
		    //// translate(0,0,300);
		    ellipse(leapToScreenX(position.getX()), leapToScreenY(position.getY()), tSize/2, tSize/2);
		    /// popMatrix();
	
		    theRepulsor.update(leapToScreenX(position.getX()), leapToScreenY(position.getY()), 300);
		    theRepulsor.display();
		  }
		}
	
	public void drawBouncers(){
	    for (int i=0; i< bouncers.size(); i++){
	        Bouncer dBouncer = bouncers.get(i);
	        /// movers[i].applyForce(gravity);
	        PVector f = theRepulsor.repulse(dBouncer);
	        dBouncer.applyForce(f);
	        dBouncer.update();
	        dBouncer.checkEdges();
	        dBouncer.display();
	    }
	}
	
	///////// FEATHERS AND LIFTERS
	public void spawnFeathers(){

	    // feathers.clear();
	    for (int i=0; i< totalFeathers; i++){
	        feathers.add(new Feather(random(1.1f,5)));  
	    } 
		
	}
	
	
	public void doLifters(){
		
	//// empty the old array
	lifters = new ArrayList();
	////// do the LEAP //////////
	hint(DISABLE_DEPTH_TEST);
		  for (Map.Entry entry : fingerPositions.entrySet()){
			  
		    Integer fingerId = (Integer) entry.getKey();
		    Vector position = (Vector) entry.getValue();

		    float tSize = map(position.getZ(), -100.0f, 100.0f, 0.0f ,20.0f);
		   ////  pushMatrix();
		    
		    //// create a lifter
		    theLifter = new Lifter(1.3f);
		    lifters.add(theLifter);
		    
		    
		    //// tell it to interact with the feather
		    theLifter.update(leapToScreenX(position.getX()), leapToScreenY(position.getY()), 300);
		    theLifter.display();
		    
		    // show finger colors
		    // fill(fingerColors.get(fingerId), 65);
		    
		    stroke(255);
		    //// fill(0);
		    strokeWeight(1);
		    ellipse(leapToScreenX(position.getX()), leapToScreenY(position.getY()), tSize*1.75f, tSize*1.75f);
		    /// popMatrix();
	
		  }
		 hint(ENABLE_DEPTH_TEST);
	}
	

	public void drawFeather(){
		 Feather dFeather = feathers.get(0);
	       
	     for(int j=0; j<lifters.size(); j++){
        	Lifter tLifter = lifters.get(j);
        	
        	if(tLifter != null){
        		PVector l = tLifter.repulse(dFeather);
        		dFeather.applyForce(l);
        	}
        	/// gets the force betweem feather and each lifter
        	

 	
	      }
	      
	      dFeather.update();
	      dFeather.checkEdges();
	      dFeather.display();
	}
		
	
	/////// FINGERDRUMS ////////
	
	public void doFingerDrums(){
		////// do the LEAP //////////
	  for (Map.Entry entry : fingerPositions.entrySet()){
		  
	    Integer fingerId = (Integer) entry.getKey();
	    Vector position = (Vector) entry.getValue();

	    // show finger colors
	    // fill(fingerColors.get(fingerId), 65);
	    fill(0,0,0,0);
	    // stroke(255);
	    // strokeWeight(0);
	    
	    /// this makes the finger tracking circles a nice size
	    float tSize = map(position.getZ(), -100.0f, 100.0f, 0.0f ,20.0f);
	  
	    
	    //// tell it to interact with the fingerdrums
	    theFingerDrums.checkHit(leapToScreenX(position.getX()), leapToScreenY(position.getY()), 300);
	    
	    
	    //// move the ellipse in the z index
	    //// translate(0,0,300);
	    ellipse(leapToScreenX(position.getX()), leapToScreenY(position.getY()), tSize/2, tSize/2);
	    /// popMatrix();

	  }
		
		theFingerDrums.display();
		int sec = (int) ( ( theTimer.getElapsedTime() - curMarker ) / 1000 );
		if ( sec != currentSec )
		{
			currentSec = sec;
			theFingerDrums.secChanged( currentSec );
			println( currentSec );
			
		}
		
		/*curMarker =  theTimer.getElapsedTime();
		int getElapsedTime() {
	        int elapsed;
	        if (running) {
	             elapsed = ( pApp.millis() - startTime);
	        }
	        else {
	            elapsed = (stopTime - startTime);
	        }
	        return elapsed;
	    }*/
	}
	
  /////////////////////////////////////
  /////// LEAP INPUT /////////////////
	/////////////////////////////////////
  public void onFrame(final Controller controller){
	  
	  Frame frame = controller.frame();
	  
	  //// finger checks
	  Hand hand = frame.hands().get(0);
	  FingerList fingers = hand.fingers();
	  
	  theAppProfile.curNumFingers = hand.fingers().count();

	  for (Finger finger : frame.fingers()){

	    int fingerId = finger.id();
	    int c = color(random(0, 255), random(0, 255), random(0, 255));  //generate random color
	    fingerColors.putIfAbsent(fingerId, c);  //if there isn't a color put color into fingerColors at fingerId
	    
	    fingerPositions.put(fingerId, finger.tipPosition());
	  }
	  
	  for (Tool tool : frame.tools()){
	    int toolId = tool.id();
	    int c = color(random(0, 255), random(0, 255), random(0, 255));
	    toolColors.putIfAbsent(toolId, c);
	    toolPositions.put(toolId, tool.tipPosition());
	  }

	  //// gesture checks
	  if (gestureCheck) {
		  
		  GestureList gestures = frame.gestures();
		  
	      for (int i = 0; i < gestures.count(); i++) {
	          Gesture gesture = gestures.get(i);
	
	          switch (gesture.type()) {
	              case TYPE_CIRCLE:
	                  CircleGesture circle = new CircleGesture(gesture);
	
	                  // Calculate clock direction using the angle between circle normal and pointable
	                  String clockwiseness;
	                  if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4) {
	                      // Clockwise if angle is less than 90 degrees
	                      clockwiseness = "clockwise";
	                  } else {
	                      clockwiseness = "counterclockwise";
	                  }
	
	                  // Calculate angle swept since last frame
	                  double sweptAngle = 0;
	                  if (circle.state() != State.STATE_START) {
	                      CircleGesture previousUpdate = new CircleGesture(controller.frame(1).gesture(circle.id()));
	                      sweptAngle = (circle.progress() - previousUpdate.progress()) * 2 * Math.PI;
	                  }
	
	                  println("Circle id: " + circle.id()
	                             + ", " + circle.state()
	                             + ", progress: " + circle.progress()
	                             + ", radius: " + circle.radius()
	                             + ", angle: " + Math.toDegrees(sweptAngle)
	                             + ", " + clockwiseness);
	                  break;
	              case TYPE_SWIPE:
	                  SwipeGesture swipe = new SwipeGesture(gesture);
	                  
	                  theGesture.id = swipe.id();
	                  theGesture.state = String.valueOf(swipe.state());

	                  // theGesture.direction = swipe.direction();
	                  theGesture.position = new PVector(swipe.position().getX(), swipe.position().getY(), swipe.position().getZ());
	                  theGesture.speed = swipe.speed();

	                  /*
	                  println("Swipe id: " + swipe.id()
	                             + ", " + swipe.state()
	                             + ", position: " + swipe.position()
	                             + ", direction: " + swipe.direction()
	                             + ", speed: " + swipe.speed());
	                             
	                             */
	                  break;
	              case TYPE_SCREEN_TAP:
	                  ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
	                  println("Screen Tap id: " + screenTap.id()
	                             + ", " + screenTap.state()
	                             + ", position: " + screenTap.position()
	                             + ", direction: " + screenTap.direction());
	                  break;
	              case TYPE_KEY_TAP:
	                  KeyTapGesture keyTap = new KeyTapGesture(gesture);
	                  println("Key Tap id: " + keyTap.id()
	                             + ", " + keyTap.state()
	                             + ", position: " + keyTap.position()
	                             + ", direction: " + keyTap.direction());
	                  break;
	              default:
	                  println("Unknown gesture type.");
	                  break;
	          }
	      }
      }

	  //// clean expired fingers
	  cleanExpired(frame);
	 
	}

  public void cleanExpired(Frame tframe){

	  for (Map.Entry entry : fingerPositions.entrySet()){
		  
		    Integer fingerId = (Integer) entry.getKey();
		    
   
		    boolean hasFinger = false;
		    
		    /// println("Cur Finger Map: " + fingerId.toString());

		    /// check all current fingers against the 
		    /// current position list
		    for (Finger finger : tframe.fingers()){
		    	int tFingerId = finger.id();
		    	try{
		    		
		    	  // for the current fingermap
		    	  // check to see if it matches the corresponding finger 
		    	  // if the finger has a position, don't remove it
		  		  if (fingerId == tFingerId && finger.tipPosition() != null) {
		  			hasFinger = true;

		  		   } else {
		  			 fingerPositions.remove(fingerId,finger.tipPosition());
		  			 /// println("NUM FINGERS : " + fingerPositions.size());
		  		   }
		  	  } catch(Exception e){
		  		  
		  	  }	
	    	
		    }
		    /// if we haven't toggled has finger
		    /// remove it from the list
		    if(!hasFinger){
		    	fingerPositions.remove(fingerId);
		    }
	  }
	  
  }

	//// this lerps the positions

	float leapToScreenX(float x){
	  float c = width / 2.0f;
	  if (x > 0.0){
	    return lerp(c, width, x/LEAP_WIDTH);
	  }
	  else{
	    return lerp(c, 0.0f, -x/LEAP_WIDTH);
	  }
	}

	float leapToScreenY(float y){
	  return lerp(height, 0.0f, y/LEAP_HEIGHT);
	}
	
	////// end leap input //////
	
	
	////////////////////////////
	//// NAVIGATION CONTROL //////////////
	//////////////////////////////////
	
	///// control events need to be implemented in main
	///// for some reason
	public void controlEvent(ControlEvent theEvent) {
		// DropdownList is of type ControlGroup.
		// A controlEvent will be triggered from inside the ControlGroup class.
		// therefore you need to check the originator of the Event with
		// if (theEvent.isGroup())
		// to avoid an error message thrown by controlP5.
		
		
		// println("P5 EVENT" + theEvent.getController().getId());
		
		if(theEvent.isController()){
			// println("CLICK" + theEvent.getController().getId());
			/// println("Name" + theEvent.getName());
		}
		if (theEvent.isFrom("MAIN MENU")){
			
			doMenu();
			/// make sure all the message boxes are hidden
			closeGameMessageBox();
		}
		
		if (theEvent.isFrom("PLAY/PAUSE")){
			doPause();
		}
		
		if (theEvent.isFrom("CLOSE")){
			
			isMenuShowing = false;
			isPaused = false;
			theMessaging.closeMessage();
			startNewGame(theMessaging.newGameID);
			

		}
		// this needs to be for all classes
		
		if (theEvent.isFrom("CLOSEGAMEMESS")){
			switch(theAppProfile.gameID){

			
			case 2:
				
				breakouts.get(0).thePopup.closeMessage();
				breakouts.get(0).doNextLevel();
				
			case 3:
				feathers.get(0).thePopup.closeMessage();
				/// start new level?
				feathers.get(0).doNextLevel();
				
				break;
				
			case 5:
				
				///// close fingerdrums popup
				theFingerDrums.thePopup.closeMessage();
				theFingerDrums.doNextLevel();
				
				break;
				
				
			default:
					
					break;
			}
			
			closeGameMessageBox();
			
		}
		
		if (theEvent.isFrom("ABOUT")){
			theMessaging.showAbout();
			
		}
		
		if (theEvent.isFrom("GAMES")){
			theMessaging.showGameMenu();
			// show the default game
			theMessaging.showGameInfo(0);
		}
		
		if (theEvent.isFrom("PROGRESS")){
		
			theMessaging.showStats();
			theMessaging.loadPlayerAchievements();
		}
		
		if (theEvent.isFrom("SETTINGS")){
		
			theMessaging.showSettings();
		}
		/// game menu actions
		if (theEvent.isFrom("PLAY GAME")){
			isMenuShowing = false;
			isPaused = false;
			theMessaging.closeMessage();
			/// only play a few games
			/*
			if(theMessaging.newGameID == 2 || theMessaging.newGameID == 3){
				startNewGame(theMessaging.newGameID);
			
			}
			*/
			
			startNewGame(theMessaging.newGameID);
		}
		
		
		if (theEvent.isFrom("FINGERBALL")){
			theMessaging.showGameInfo(0);
			// theMessaging.messageState = "showMenu";
		}
		if (theEvent.isFrom("MULTIFEET")){
			theMessaging.showGameInfo(1);
			// theMessaging.messageState = "showMenu";
		}
		
		if (theEvent.isFrom("FINGERBLOCKS")){
			theMessaging.showGameInfo(2);
			// theMessaging.messageState = "showMenu";
		}
		if (theEvent.isFrom("FEATHERWEIGHT")){
			theMessaging.showGameInfo(3);
			// theMessaging.messageState = "showMenu";
		}
		if (theEvent.isFrom("GRABBER")){
			theMessaging.showGameInfo(4);
		}
		if (theEvent.isFrom("FINGERDRUMS")){
		
			theMessaging.showGameInfo(5);
			// theMessaging.messageState = "showMenu";
		}
	
	}
	
	///// MAKES SURE THE GAME MESSAGE DIALOGS ARE ALL CLOSED
	private void closeGameMessageBox(){

		breakouts.get(0).thePopup.closeMessage();
		feathers.get(0).thePopup.closeMessage();
		theFingerDrums.thePopup.closeMessage();
	}
	///////////////////////////////
	////// GAME STATE FUNCTION ////
	//////////////////////////////
	
	public void doPause(){
		/// if the menu is showing
		/// hide it and unpause
		if(isMenuShowing){
			
			isPaused = false;
			isMenuShowing = false;
			theTimer.running = true;
			theMessaging.closeMessage();

		
			//// otherwise, let's pause the current game
		} else {
			if(isPaused){
				
				isPaused = false;
				theTimer.running = true;
				/// playPauseCurrentGame(false);
			} else {
				
				isPaused = true;
				theTimer.running = false;
				/// playPauseCurrentGame(true);
			}
		}
		
		playPauseCurrentGame(isPaused);

	}
	
	////// pause current game
	public void playPauseCurrentGame(boolean playPause){
		
		switch(theAppProfile.gameID){
		
		case 0:
			
			break;
			
		case 1:
			
			break;
			
		case 2:
			
			breakouts.get(0).gamePaused = playPause;
			break;
			
			
		case 3:
			feathers.get(0).gamePaused = playPause;
			break;
			
			
		case 4:
			
			
			break;
			
			
		case 5:
			
			theFingerDrums.gamePaused = playPause;
			break;
		
		
		
		}
		
	}
	
	public void doUnPause(){
		isPaused = false;
		
		// do messaging
	}
	
	///// show default menu
	public void doMenu(){
		isMenuShowing = true;
		isPaused = true;
		theMessaging.showAbout();
		playPauseCurrentGame(isPaused);

	}
	
	//////////////////////////////
	///// CREATE INTERFACE ////
	//////////////////////////////
	
	private void initGUI(){
		
		 PImage[] playPauseImgs = {b_PlayPause,b_PlayPauseR,b_PlayPauseR};
		 PImage[] menuImgs = {b_MainMenu,b_MainMenuR,b_MainMenuR};
		 
		
		 playPauseButton = cp5.addButton("MAIN MENU")
				  .setPosition(theAppProfile.theWidth - 320, topMargin)
				  .setImages(menuImgs)
				  .updateSize()
				  ;
		 menuButton = cp5.addButton("PLAY/PAUSE")
				  .setPosition(theAppProfile.theWidth - 320, topMargin *3.5f)
				  .setImages(playPauseImgs)
				  .updateSize()
				  
				  ;
	}

	private void doGUI(){
		
		
		/// parse score
		
		/// this is too fancy
		image(footerBground, 0, theAppProfile.theHeight - footerBground.height/1.5f);
		
		//// need to assign this to specific game
		/// not player score in general

		image(controlPanel, 0, 0);

		/*
		if(isMenuShowing == false){
			
			theScore = String.valueOf(thePlayerProfile.GameStats.get(theAppProfile.gameID).curScore);
			
			int hour = theTimer.hour();
			int min = theTimer.minute();
			int sec = theTimer.second();
			
			if(theTimer.hour() <=0){
				hour = 000;
			}
			if(theTimer.minute() <=0){
				min = 000;
			}
			if(theTimer.second() <=0){
				sec = 000;
			}
			theTime = hour + ":" + min + ":" + sec + ":" + theTimer.milisecond(); /// String.valueOf(theAppProfile.scoredata);
	
			/// diaplay config
			theRank = theAppProfile.ranktypes.get(theAppProfile.rankID);
			theGameType = theAppProfile.gameMode.get(theAppProfile.gameID);
	
			textFont(mainNavFont, 28);
			fill(whiteNav);
			text(theGameType, theAppProfile.theWidth/2 - 150, 100);
			text(theRank, theAppProfile.theWidth - 180, theAppProfile.theHeight - 50);
			/// text(theTime, 125, theAppProfile.theHeight - 10);
			
			/// display current score
			//// check for type of game it is
			/// for multifeet, you give distance
			/// not points
			if(theGameType == "MULTIFEET"){

				String tScore = theScore + " feet";
				textFont(mainNavFont, 28);
				text(tScore, 125, theAppProfile.theHeight - 50);
				
			} else {
				textFont(mainNavFont, 28);
				text(theScore, 125, theAppProfile.theHeight - 50);
				
			}
			

		
		}

		*/
		
	}
	
	//////////////////////////////////////
	////////// ACHIEVEMENTS /////////////
	//////////////////////////////////////
	
	public void initAchievement(String tCheev){
		/// set the time to start
		/// displaying the achievment
		
		
		cheevoName = tCheev;
		
		/// check to see if the player
		/// has this achievment
		boolean hasCheevo = false;
		for(int j=0; j<thePlayerProfile.CheevoNames.size(); j++){
			

			if(thePlayerProfile.CheevoNames.get(j).equals(cheevoName)){
				hasCheevo = true;
				println("HAS: " + thePlayerProfile.CheevoNames.get(j) + " new: " + cheevoName);
			}
		}
		
		/// if player doesn't have this achievement
		/// then get the info, display it, and add to the
		/// player data array
		
		
		if(hasCheevo == false){	
			
			///// wait for the return, then load the image
			showCheevo = true;
			
			//// reset the achievment display time
			curMarker =  theTimer.getElapsedTime();
			/// get the achievment data from the game profiles
			
			
			for (int i=0; i<thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoNames.size(); i++){
				
				if(cheevoName.equals(thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoNames.get(i))){
					println("Cheevo FOUND: " + cheevoPath + " " + cheevoDesc + " " + cheevoName);
					// add cheevo to the player profile
					thePlayerProfile.addAchievement(cheevoName);
					hasCheevo = true;
					// re-init the cheevo display in "progress"
					/// should probably do this in a different thread
					// theMessaging.loadPlayerAchievements();
					
					/// this doesnt work
					/// thread("theMessaging.loadPlayerAchievments");
					/// get ready to display the badge
					cheevoDesc = thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoDescription.get(i);
					cheevoPath = thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoImage.get(i);
				}
			}

			try{

				cheevoBadge = loadImage(cheevoPath);
			
			} catch (Exception e){
				
				println("Thread Trouble!" ) ;
			}
		} else {
			showCheevo = false;
		}
		
	}
	public void showAchievment(){
		
		 //// draw the achievment
		/// println("SHOW CHEEVO");
		float tX = theAppProfile.theWidth/2 - cheevoBground.width/2;
		float tY = theAppProfile.theHeight - cheevoBground.height - 20;
		String cheevData = "Achievment: " + cheevoName + "\n";
		
		
			/// println("Cheevo name: " + cheevoName);
			image(cheevoBground, tX, tY);
			image(cheevoBadge, tX + 27, tY + 28);
		try{
			
			textFont(ScoreFont, 22);
			fill(255);
			text(cheevData, tX + 100, tY + 45);
			textFont(ScoreFont, 16);
			text(cheevoDesc, tX + 100, tY + 50, 320, 200);
			
			 /// retrieve the stat info
			 if(theTimer.getElapsedTime() - 5000 >= curMarker){

				hideAchievement();
				 
				 
			 }
		} catch (Exception e){
			
			println("can't draw cheevo: " + e);
		}
		
		 
	 }
	 
	 public void hideAchievement(){
		 showCheevo = false;
		 println("HIDE CHEEVO");
		 
	 }
	
	////////////////////////////////////////
	///////// KEYBOARD INPUT ///////////////
	////////////////////////////////////////
	
	public void keyPressed() {
		
	    if (key == CODED) {
	    	if (keyCode == RIGHT) {
			 	println("next game");
				doNextGame();
		      
		    } 
	    	if (keyCode == LEFT) {
			 	println("previous game");
				doPrevGame();
			      
			} 
	    	
	    	
		 }
	    
	    //// reg keys
		if (key == 'r') {

		}
		if (key == ' '){
			
			doPause();
		}
		
		if (key == 's'){
			thePlayerProfile.savePlayerData();
		}
		
		if (key == 'm'){
			
			doMenu();
			
			
		}
		
		

	} /// end keypress code

	
	
}/// end class
