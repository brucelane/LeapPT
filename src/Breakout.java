import processing.core.*;
import processing.opengl.PGraphicsOpenGL;

import java.util.ArrayList;


class Breakout{
	
	PApplet pApp;
	AppProfile theAppProfile;
	PlayerProfile thePlayerProfile;
	  
	SoundControl theSoundControl;
	  
    PVector location;
    PVector velocity;
    PVector acceleration;
    PVector wind;
    PVector gravity;
    
    int theColor;
    int theR = 255;
    int theG = 0;
    int theB = 0;
    int theA = 165;
    
    float theSpin = 0.01f;

    float mass;
    int ballSize = 25;
    
    boolean hasImpact = false;
    boolean hasDamage = false;
    
    /// box vars
    int numRows;
    int numCols;
	float boxWidth;
	float boxHeight;
    int numBoxes;
    int boxColor;
    int boxTop = 250;
    
    //// paddle vars
    float paddleHeight;
    float currentFloor;
    
    int impactCounter;
    
    /// game params
    int curLevel = 1;
    boolean gamePaused = true;
    GameMessaging thePopup;
    
    
    ArrayList<BreakoutBox> BreakBoxes = new ArrayList();
    
    PImage bottomBorder;
    String borderPath = "data/games/bottom_border_breakout.png";
    
    /// the mass, the x, the y);
    Breakout(float m, float x, float y){
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;
    	theSoundControl = theSoundControl.getInstance();
    	thePlayerProfile = thePlayerProfile.getInstance();
        thePopup = thePopup.getInstance();
    	 // = new SoundControl();
    	
    	theColor = pApp.color(theR,theG,theB,theA);
    	
        mass = m;

        
    }
    
////level setup and init
   public void startNewGame(){
	   gamePaused = true;
  	 /// do initial messags
  	   thePopup.initMessage(0, 0, "WELCOME TO BREAKOUT!", "This game is designed to strenghten your upper and forearm co-ordination by using lateralmovements. First, we'll start off easy-- move your hand back and forth to bounce the ball against the block screen five times!", 255, 255);
  	   setLevelParams();
  	   spawnBoxes(curLevel);
  	
  }
   
////////THIS IS CALLED FROM THE DIALOG CLOSE BUTTON
////////YOU NEVER HIT THIS UNLESS YOU"RE STARTING A NEW LEVEL
	public void doNextLevel(){
		
		switch (curLevel){
		
		case 0:
			
			break;
	  
		case 1:
			

	        
			break;
			
		
		case 2:

	        
		    break;
		case 3:
			//// TIMER level
			// theTimer.stop();
			// theTimer.start();
			break;
			
		case 4:
			//// hover level
			// theTimer.stop();
			// theTimer.start();
			break;
		case 5:
			
			break;
			
		default:
			
			break;
			
		}
		
		/// update game params
		setLevelParams();
		spawnBoxes(curLevel);
		//// and unpause
		gamePaused = false;
		
	}
	
	public void setLevelParams(){
		switch (curLevel){
			
			case 0:
				
				break;
		   ///////// finger level
			case 1:
				
				/// set the location randomly, but make sure it's below the blocks!
				location = new PVector(pApp.random(theAppProfile.theWidth), boxTop + numRows*boxHeight);
		        velocity = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
		        acceleration = new PVector(0.1f,0.2f);
		        
		        bottomBorder = pApp.loadImage(borderPath);
		        
		        /// setup boxes
		        numCols = 8;
		        numRows = 2;
		        boxWidth = theAppProfile.theWidth/numCols;
		        boxHeight = 20f;
		        spawnBoxes(curLevel);
		        
				break;
				
			//////// hand level	
			case 2:
			    	
				location = new PVector(pApp.random(theAppProfile.theWidth), pApp.random(theAppProfile.theHeight/2));
		        velocity = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
		        acceleration = new PVector(0.1f,0.2f);
		        
		        bottomBorder = pApp.loadImage(borderPath);
		        
		        /// setup boxes
		        numCols = 8;
		        numRows = 2;
		        boxWidth = theAppProfile.theWidth/numCols;
		        boxHeight = 20f;
		        spawnBoxes(curLevel);
		        
			    break;
			case 3:
				//// TIMER level
				// theTimer.stop();
				// theTimer.start();
				break;
				
			case 4:
				//// hover level
				// theTimer.stop();
				// theTimer.start();
				break;
			case 5:
				
				break;
				
			default:
				
				break;
				
		}
			
		
		
	}
	
	////// THIS IS CALLED WHEN LEVEL FINISHING PARAMETERS ARE MET ////////
	private void endLevel(){
		switch(curLevel){
		
		
		case 1:
			gamePaused = true;
        	/// show message
        	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "Great job! You have passed level one... but that was the easy part.\n\nNow you have to break a whole lot of boxes!", 255, 255);
        	/// do next level
        	curLevel = 2;
			break;
			
		case 2:
			gamePaused = true;
        	/// show message
        	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "Great job! You have passed level one... but that was the easy part.\n\nNow you have to break a whole lot of boxes!", 255, 255);
        	/// do next level
        	curLevel = 3;
			break;
			
		case 3:
			
			gamePaused = true;
        	/// show message
        	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "Great job! You have passed level two... but don't get penisy.\n\nNow you have to break a whole lot of boxes!", 255, 255);
        	/// do next level
        	curLevel = 4;
			break;
		
		}
		
	}

    /////// SPAWN BOXES //////////////
    private void spawnBoxes(int tLevel){
    	BreakBoxes.clear();
    	boxWidth = theAppProfile.theWidth/numCols;
    	boxHeight = 20f;
    	boxColor = pApp.color(255, 0, 0,165);
    	for (int i = 0; i< numRows; i++){
    		
    		for(int j = 0; j<numCols; j++){

    			BreakoutBox tBox = new BreakoutBox(j * boxWidth, i* boxHeight + 250, 0.0f, boxWidth, boxHeight, boxColor);
    			BreakBoxes.add(tBox);
    		}
    	}
    }
    
    /// force
    void applyForce(PVector force){
        PVector f = PVector.div(force,mass);
        acceleration.add(f);
        
    }
    
    
    /////// UPDATE POSITION AND CHECK EDGES ////////////////
    
    void update(){
    	
    	if(gamePaused == false){
	        velocity.add(acceleration);
	        location.add(velocity);
	        acceleration.mult(0);
	        
	        if(theR < 255){
	        	theR +=10;
	
	        }
	        if(theG > 0){
	        	theG -=10;
	
	        }
	        if(theB > 0){
	        	theB -=10;
	
	        }
	        if(theA > 165){
	        	theA -=10;
	
	        } 
	        
	        theColor = pApp.color(theR,theG,theB,theA);
        
    	}
    }
    
    
    
    void checkEdges(){
    	
    	//// check for boxes
    	
    	if(location.y > boxTop && location.y < boxTop + (numRows * boxHeight)){
    		
    		checkBlocks();
    	}
    	
    	
    	
    	//// check for walls, ceiling, and floor
        if(location.x>theAppProfile.theWidth){
            location.x = theAppProfile.theWidth;
            velocity.x *= -.65;
            doImpactSounds();
            // theAppProfile.scoredata += 23;

        } else if (location.x<0){
            velocity.x *= -.65;
            location.x = 0;
            doImpactSounds();
            // theAppProfile.scoredata += 23;
            
        }
        
        if(location.y>currentFloor){
            velocity.y *= -1;
            location.y = currentFloor;
            // doImpactSounds();

            impactCounter = 255;
            hasDamage = true;
            theSoundControl.playStarWarsSound(6);
            // theAppProfile.scoredata += 23;


        } else if (location.y<0){
            location.y = 0;  //// make sure the location doesn't go above the screen
            velocity.y *=-.25;
            doImpactSounds();
            /// theAppProfile.scoredata += 23;

        }
        
        thePlayerProfile.GameStats.get(theAppProfile.gameID).curScore += 23;
        
    }
    
    
    //////// CHECK BALL IMPACT ON BOXES////////////
    private void checkBlocks(){
    	

    	for(int i = 0; i< BreakBoxes.size(); i++){
    		
    		//// check all blocks
    		BreakoutBox tBox = BreakBoxes.get(i);
    		
    		if(tBox.isHit == false && location.x > tBox.theX && location.x < tBox.theX + tBox.theWidth && location.y > tBox.theY && location.y < tBox.theY + tBox.theHeight){
    		    
    			// do bounce
    			if(location.y > tBox.theY){
    				velocity.y *= .75;
    				
    			}
    			if(location.y < tBox.theY + tBox.theHeight){
    				velocity.y *= -1.75;
    				
    			}
    			//// are they live
    			tBox.doHit();
    		}	
    	}
    	
    	//// have you hit all the blocks?
    	
    	///*
    	boolean isDone = true;
    	for(int k = 0; k< BreakBoxes.size(); k++){
    		BreakoutBox tBox = BreakBoxes.get(k);
    		/// if one box is not hit
    		/// then the level is not done
    		if(tBox.isHit == false){
    			isDone = false;
    		} 
    	}
    	
    	if(isDone){
    		endLevel();
    	}
    	//*/
    	
    }

    void doImpactColor(){

        theR = 12;
        theG = 255;
        theB = 12;
        theA = 255;
 
    }

    void doImpactSounds(){
    	
    	/// switch
       
	   int theRnd = (int)pApp.random(5);
	   /// pApp.println("wall hit: " + theRnd);
	   theSoundControl.playLaserSounds(theRnd);

    }
    
    void doPaddleHit(){
       int theRnd = (int)pApp.random(4);
   	   theSoundControl.playStarWarsSound(theRnd);
   	   /// pApp.println(theRnd);
   	   thePlayerProfile.GameStats.get(theAppProfile.gameID).curScore += 123;
    }
    
    
    ////////// DISPLAY BGROUND AND BOXES ////////////////
    void display(){
    	
    	drawBoxes();
    	
    	
    	/*
    	if(hasDamage && impactCounter >0){
    		
    		  pApp.fill(impactCounter,0,0);
              pApp.rect(0,0,theAppProfile.theWidth, theAppProfile.theHeight);
              impactCounter -= 75;
    	}
    	
    	if(impactCounter <=0){
    		hasDamage = false;
    	}
    	*/
    	
    	if(gamePaused == false){
	        pApp.stroke(255,255,255, 65);
	        pApp.strokeWeight(1);
	        pApp.fill(theColor);
	
	        pApp.pushMatrix();
	        pApp.translate(location.x, location.y, location.z); /// x,y and z?
	        
	      /// add the x and y position to the movement
	        pApp.rotateY(pApp.map(location.y, 0,600, 0.5f, -0.5f));
	        pApp.rotateX(pApp.map(location.x, 0,600, 1.9f, -1.9f));
	        if(hasImpact){
	        	theSpin +=2.1;
	            pApp.rotateZ(theSpin);
	        } else {
	        	theSpin = .001f;
	        }
	        
	        pApp.sphere(20);
	        // pApp.sphere(50);
	        pApp.popMatrix();
        
    	}

        pApp.image(bottomBorder, 0, currentFloor + bottomBorder.height);
        
        if(thePopup.showingMessage == true){
     	   thePopup.drawMessage();
     	   
        }
    }
    
    
    /////// draw all boxes
    private void drawBoxes(){
    	
    	for(int i=0; i<BreakBoxes.size(); i++){
    		BreakoutBox tBox = BreakBoxes.get(i);
    		if(tBox.isLive == true){
    			tBox.drawBox();
    		} else {
    			
    			// BreakBoxes.remove(tBox);
    			
    		}
    	}
    	
    }
    
    
    
    /////////////////
}
/// end breakout class
