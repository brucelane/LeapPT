import processing.core.*;
import processing.opengl.PGraphicsOpenGL;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

/// leap motion
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Tool;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.processing.LeapMotion;

/// minim audio
import ddf.minim.AudioSample;
import ddf.minim.Minim;


public class Main extends PApplet{
	
	/// leap
	LeapMotion leapMotion;
	
	/// finger arrays
	ConcurrentMap<Integer, Vector> fingerPositions;
	ConcurrentMap<Integer, Vector> toolPositions;
	ConcurrentMap<Integer, Integer> fingerColors;
	ConcurrentMap<Integer, Integer> toolColors;
	
	// text and UI display
	PImage controlPanel;
	
	String theScore = "0";
	String theTime = "0";
	String theRank = "noob";
	String theGameType = "null";
	
	/// game state controls
	int gameID = 0;
	
	///// fonts
	PFont ScoreFont = createFont("Neutra Text",22, true); /// normal fonts
	PFont pfont = createFont("Neutra Text",10, true); // use true/false for smooth/no-smooth for Control fonts

	
	int numFingers = 0;

	static float LEAP_WIDTH = 200.0f; // in mm
	static float LEAP_HEIGHT = 700.0f; // in mm
	
	int tWidth;
	int tHeight;

	float theX = 0;
	float theY = 0;
	float theZ = 0;

	float bdifx = 0.0f; 
	float bdify = 0.0f; 
	
	float gravWeight = .5f;
	
	///// control and images
	String panelPath = "data/control_panel.png";

	// applet
	AppProfile theAppProfile;
	
	/// arrays
	ArrayList<Mover> movers;
	
	int totalMovers = 1;
	Mover theMover;
	Attractor theAttractor;
	
	TimerClass theTimer;

	public void setup(){	
		  
	  /// set up app profile
	  theAppProfile = theAppProfile.getInstance();
	  theAppProfile.SetPApp(this);
	  
	  //
	  movers = new ArrayList();
	  
	  //
	  theTimer = new TimerClass();
	  
	  tWidth = theAppProfile.theWidth;
	  tHeight = theAppProfile.theHeight;
	  // size(16*50, 9*50);
	 //  size(800,600, OPENGL);
	  size(tWidth,tHeight, OPENGL);
	  background(20);
	  frameRate(60);
	  ellipseMode(CENTER);

	  leapMotion = new LeapMotion(this);
	  fingerColors = new ConcurrentHashMap<Integer, Integer>();
	  toolColors = new ConcurrentHashMap<Integer, Integer>();
	  fingerPositions = new ConcurrentHashMap<Integer, Vector>();
	  toolPositions = new ConcurrentHashMap<Integer, Vector>();
	  
	  /// smooth();
	  
	  // movers
	  spawnMovers();
	  theAttractor = new Attractor(gravWeight);

	  /// text images panels
	  controlPanel = loadImage(panelPath);
	  /// init the GUI
	  initGUI();
	  
	  theTimer.start();

	}
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

	public void draw(){
	  background(0);
	  pushMatrix();
	  translate(0,0,-500);
	  popMatrix();

	  for (Map.Entry entry : fingerPositions.entrySet()){
		  
	    Integer fingerId = (Integer) entry.getKey();
	    Vector position = (Vector) entry.getValue();

	    // fill(fingerColors.get(fingerId), 65);
	    stroke(255);
	    strokeWeight(1);
	    
	    float tSize = map(position.getZ(), -100.0f, 100.0f, 0.0f ,20.0f);
	    pushMatrix();
	    translate(0,0,300);
	    ellipse(leapToScreenX(position.getX()), leapToScreenY(position.getY()), tSize/2, tSize/2);
	    popMatrix();

	    /// hitTest(leapToScreenX(position.getX()), leapToScreenY(position.getY()), position.getZ());

	    theAttractor.update(leapToScreenX(position.getX()), leapToScreenY(position.getY()), position.getZ());

		theAttractor.display();


	  }
	  drawMovers();

	  image(controlPanel, 0, 0);
	  doGUI();

	}
	
	void drawMovers(){
	    
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
	
	///////////////////////////////////////
	///// GAME STATE CONTROL /////////////
	/////////////////////////////////////
	public void doNextGame(){
		
	}
	
	public void doPrevGame(){
		
		
	}
	
	public void renderGame(){
		
		switch (gameID){
		
		
		
		
		
		
		}
		
		
		
	}

  /////////////////////////////////////
  /////// LEAP INPUT /////////////////
	/////////////////////////////////////
  public void onFrame(final Controller controller){
	  
	  Frame frame = controller.frame();
	  
	  Hand hand = frame.hands().get(0);
	  FingerList fingers = hand.fingers();


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

	  // todo:  clean up expired finger/toolIds
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
	
	//////////////////////////////
	///// CREATE INTERFACE ////
	//////////////////////////////

	private void initGUI() {
		
		
		
	}

	private void doGUI(){

		/// parse score

		
		try{
			
			int oldScore = parseInt(theScore); // theAppProfile.scoredata;
			if(oldScore < theAppProfile.scoredata){
			oldScore += 11;
			}
			theScore = String.valueOf(oldScore);
			
		} catch (Exception e){
			println("error parsing score: " + e);
		}
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
		theRank = theAppProfile.rankdata;
		theGameType = theAppProfile.gametypedata;

		textFont(ScoreFont, 22);
		fill(255,255,0);
		text(theGameType, 160, 38);
		text(theRank, 90, 70);
		text(theTime, 470, 72);
		
		fill(255);
		textFont(ScoreFont, 28);
		text(theScore, 830, 100);
		
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
	    	if (keyCode == RIGHT) {
			 	println("next game");
				doPrevGame();
			      
			} 
		 }
	    
	    //// reg keys
		if (key == 'r') {

		}
		if (key == 'q'){

		}

	}

	
	
}
/// end class
