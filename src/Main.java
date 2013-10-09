import processing.core.*;
import processing.opengl.PGraphicsOpenGL;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.EventListener;
import java.util.EventObject;

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
import com.leapmotion.leap.processing.LeapMotion;

import controlP5.ControlEvent;
import controlP5.ControlP5;
/// minim audio
import ddf.minim.AudioSample;
import ddf.minim.Minim;


public class Main extends PApplet{
	
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
	
	// text and UI display
	PImage controlPanel;
	PImage cheevoBadge;
	PImage cheevoBground;
	
	String chevBgroundPath = "data/achievement_bground.png";
	
	 //// interface elements
	ControlP5 cp5;
	ControlP5 ControlEvent;
	
	String theScore = "0";
	String theTime = "0";
	String theRank = "";
	String theGameType = "null";
	
	/// game state data
	boolean isPaused = false;
	
	
	///// fonts
	PFont ScoreFont = createFont("Neutra Text",22, true); /// normal fonts
	PFont pfont = createFont("Neutra Text",10, true); // use true/false for smooth/no-smooth for Control fonts
	
	int numFingers = 0;

	static float LEAP_WIDTH = 200.0f; // in mm
	static float LEAP_HEIGHT = 700.0f; // in mm
	
	int tWidth = 1024;
	int tHeight = 768;

	float theX = 0;
	float theY = 0;
	float theZ = 0;
	
	///// control and images
	String panelPath = "data/control_panel2.png";

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
	
	TimerClass theTimer;
	
	float curMarker; // marker for particular time
	
	MyEventListener listener;

	public void setup(){	
	  size(tWidth,tHeight, OPENGL);
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
	  //
	  //
	  theTimer = new TimerClass();
	  
	  tWidth = theAppProfile.theWidth;
	  tHeight = theAppProfile.theHeight;
	  // size(16*50, 9*50);
	 //  size(800,600, OPENGL);

	  background(20);
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
	  
	  spawnSpinners();
  	  //
	  spawnBreakouts();
	  
	  //	BackgroundTiles theBground;
	  theBground = new BackgroundTiles(0,0,0);
	  theAttractor = new Attractor(gravWeight);
	  theRepulsor = new Repulsor(gravWeight);
	  theShaker = new Shaker(0);
	  thePaddle = new Paddle(gravWeight);
	  
	  
	  
	// add event listener to the paddle
	  thePaddle.addMyEventListener(new MyEventListener() {
      public void myEventOccurred(MyEvent evt, String tCheev) {
          /// System.out.println("fired");
          initAchievement(tCheev);
        }
      });
  

	  /// text images panels
	  controlPanel = loadImage(panelPath);
	  cheevoBground = loadImage(chevBgroundPath);
	  /// init the GUI
	  initGUI();

	}
	
	//// listeners

	
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
			 println("Name" + theEvent.getName());
		  }
		  if (theEvent.isFrom("CLOSE")){
			  isPaused = false;
			  theMessaging.closeMessage();
		  }
		  
		  if (theEvent.isFrom("GAMES MENU")){
			  theMessaging.showGameMenu();
			  
		  }
		  
		  if (theEvent.isFrom("PROGRESS")){

			  theMessaging.showStats();
		  }
		  
		  if (theEvent.isFrom("SETTINGS")){

			  theMessaging.showSettings();
		  }
		  /// game menu actions
		  if (theEvent.isFrom("PLAY GAME")){
			 
			  isPaused = false;
			  theMessaging.closeMessage();
			  startNewGame(theMessaging.newGameID);
		  }
		  
		  
		  if (theEvent.isFrom("GAME 0")){
			  theMessaging.showGameInfo(0);
			  // theMessaging.messageState = "showMenu";
		  }
		  if (theEvent.isFrom("GAME 1")){
			  theMessaging.showGameInfo(1);
			  // theMessaging.messageState = "showMenu";
		  }
		  
		  if (theEvent.isFrom("GAME 2")){
			  theMessaging.showGameInfo(2);
			  // theMessaging.messageState = "showMenu";
		  }
		  if (theEvent.isFrom("GAME 3")){
			  theMessaging.showGameInfo(3);
			  // theMessaging.messageState = "showMenu";
		  }
		  if (theEvent.isFrom("GAME 4")){
			  theMessaging.showGameInfo(4);
			  theMessaging.messageState = "showMenu";
		  }
		  if (theEvent.isFrom("GAME 5")){
			  
			  theMessaging.showGameInfo(5);
			  // theMessaging.messageState = "showMenu";
		  }
		  
	}
	

	public void draw(){
		
	  //// make a nice background ///
	  background(0);
	  
	  
	  
	  if(!isPaused){
		  renderGame();
		  doGUI();
	  } else {
		  
		  doGUI();
		  theMessaging.drawMessageBox();
	  }
	  
	  

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
			doAttractor();
			drawMovers();
			break;
			
		case 4:
			doAttractor();
			drawMovers();
			break;
		
		case 5:
			
			break;	
		}
		
		if(showCheevo){
			
			showAchievment();
		}
		
	}
	
	/////////////// 
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
	
	
	///////// BREAKOUT ////////////////
	
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

	float leapToScreenX(float x)
	{
	  float c = width / 2.0f;
	  if (x > 0.0)
	  {
	    return lerp(c, width, x/LEAP_WIDTH);
	  }
	  else
	  {
	    return lerp(c, 0.0f, -x/LEAP_WIDTH);
	  }
	}

	float leapToScreenY(float y)
	{
	  return lerp(height, 0.0f, y/LEAP_HEIGHT);
	}
	
	////// end leap input //////
	
	///////////////////////////////
	////// GAME STATE FUNCTION ////
	//////////////////////////////
	
	public void doPause(){
		isPaused = true;
		
		/// do messaging
	}
	
	public void doUnPause(){
		isPaused = false;
		
		// do messaging
	}
	
	//////////////////////////////
	///// CREATE INTERFACE ////
	//////////////////////////////

	private void initGUI() {
		
		
		
	}

	private void doGUI(){

		image(controlPanel, 0, 0);
		
		/// parse score
		
		/// this is too fancy
	
		theScore = String.valueOf(theAppProfile.scoredata);
		
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

		textFont(ScoreFont, 22);
		fill(255,255,0);
		text(theGameType, 160, 38);
		text(theRank, 90, 70);
		text(theTime, 470, 72);

		fill(255);
		
		//// check for type of game it is
		
		if(theGameType == "MULTI FEET"){
			
			String tScore = theScore + " feet";
			textFont(ScoreFont, 28);
			text(tScore, 830, 100);
			
		} else {
			textFont(ScoreFont, 28);
			text(theScore, 830, 100);
			
		}
		
		
	}
	
	//////////////////////////////////////
	////////// ACHIEVEMENTS /////////////
	//////////////////////////////////////
	
	public void initAchievement(String tCheev){
		curMarker =  theTimer.getElapsedTime();
		
		cheevoName = tCheev;
		/// println("mark: " + curMarker + " " + cheevoName);
		
		/// make sure we don't already have this achievement
		boolean hasCheevo = false;
		for(int j=0; j<thePlayerProfile.CheevoNames.size(); j++){
			
			println("HAS: " + thePlayerProfile.CheevoNames.get(j) + " new: " + cheevoName);
			if(thePlayerProfile.CheevoNames.get(j).equals(cheevoName)){
				hasCheevo = true;
				
				println("HAS CHEEVO");
				
			}
		}
		
			
		/// if player doesn't have this achievement
		if(hasCheevo == false){	
			showCheevo = true;
			/// get the achievment data from the game profiles
			for (int i=0; i<thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoNames.size(); i++){
				/*
				println("Cheevo NAME: " + thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoNames.get(i));
				println("Cheevo DESCRIPTION: " + thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoDescription.get(i));
				println("Cheevo IMAGE: " + thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoImage.get(i));
				*/
				if(cheevoName.equals(thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoNames.get(i))){
					println("Cheevo FOUND: " + cheevoPath + " " + cheevoDesc + " " + cheevoName);
					// add cheevo to the player profile
					thePlayerProfile.addAchievement(cheevoName);
					cheevoDesc = thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoDescription.get(i);
					cheevoPath = thePlayerProfile.GameStats.get(theAppProfile.gameID).CheevoImage.get(i);
				}
			}
			
			cheevoBadge = loadImage(cheevoPath);
		}
		
	}
	public void showAchievment(){
		
		 //// draw the achievment
		/// println("SHOW CHEEVO");
		float tX = theAppProfile.theWidth/2 - cheevoBground.width/2;
		float tY = theAppProfile.theHeight - cheevoBground.height - 100;
		String cheevData = "Achievment Unlocked: " + cheevoName + "\n";
		image(cheevoBground, tX, tY);
		image(cheevoBadge, tX + 30, tY + 30);
		
		textFont(ScoreFont, 22);
		fill(255);
		text(cheevData, tX + 100, tY + 50);
		textFont(ScoreFont, 18);
		text(cheevoDesc, tX + 100, tY + 70);
		
		 /// retrieve the stat info
		 if(theTimer.getElapsedTime() - 5000 >= curMarker){

			hideAchievement();
			 
			 
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
		if (key == 'a'){
			
		}
		
		if (key == 's'){
			thePlayerProfile.savePlayerData();
		}
		
		if (key == 'p'){
			if(isPaused){
				isPaused = false;
				
			} else {
				theMessaging.showGameMenu();
				// it's paused! unPause it!
				isPaused = true;
			}
		}
		
		

	} /// end keypress code

	
	
}/// end class
