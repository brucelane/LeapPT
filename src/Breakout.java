import processing.core.*;
// import processing.opengl.PGraphicsOpenGL;

import java.util.ArrayList;
import javax.swing.event.EventListenerList;

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
    int boxTop = 150;
    
    //// paddle vars
    float paddleHeight;
    float currentFloor;
    
    int impactCounter;
    
    /// game params
    int curLevel = 1;
    boolean gamePaused = true;
    GameMessaging thePopup;
    
    ///// THIS IS TEMPORARY AND WILL BE REMOVED
    boolean hasFirstBounce = false;
    boolean hasDestroyer = false;
    boolean hasBreaking = false;
    
    
    ArrayList<BreakoutBox> BreakBoxes = new ArrayList();
    
    PImage bottomBorder;
    String borderPath = "data/games/bottom_border_breakout.png";
    

    // listeners
    protected EventListenerList listenerList = new EventListenerList();
    
    /// the mass, the x, the y);
    Breakout(float m, float x, float y){
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;
    	theSoundControl = theSoundControl.getInstance();
    	thePlayerProfile = thePlayerProfile.getInstance();
        thePopup = thePopup.getInstance();
    	 // = new SoundControl();
    	
    	theColor = pApp.color(theR,theG,theB,theA);
    	bottomBorder = pApp.loadImage(borderPath);
        mass = m;

        
    }
    
////level setup and init
   public void startNewGame(){
	   gamePaused = true;
  	 /// do initial messags
  	   thePopup.initMessage(0, 0, "WELCOME TO FINGERBLOCKS!", "This game is designed to strenghten your upper and forearm co-ordination by using lateralmovements. First, we'll start off easy-- move your hand back and forth to bounce the ball against the blocks. For now, you only have two rows to get through.", null, 255, 255);
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
		
		pApp.println("CURRENT LEVLE: " + curLevel);
		
	}
	
	public void setLevelParams(){
		switch (curLevel){
			
			case 0:
				
				break;
		   ///////// big blocks level
			case 1:
				
				/// set the location randomly, but make sure it's below the blocks!
				location = new PVector(pApp.random(theAppProfile.theWidth), boxTop + numRows*boxHeight);
		        velocity = new PVector(pApp.random(-1.1f,5.1f),pApp.random(-0.1f,0.1f));
		        acceleration = new PVector(0.1f,0.2f);
		        /// setup boxes
		        numCols = 5;
		        numRows = 2;
		        boxWidth = 204.8f; //theAppProfile.theWidth/numCols;
		        boxHeight = 60f;
		        spawnBoxes(curLevel);
		        
				break;
				
			//////// small blocks levle
			case 2:
			    	
				location = new PVector(pApp.random(theAppProfile.theWidth), boxTop + numRows*boxHeight + 10);
		        velocity = new PVector(pApp.random(-0.1f,5.1f),pApp.random(-0.1f,0.1f));
		        acceleration = new PVector(0.1f,0.2f);
		        
		        
		        /// setup boxes
		        numCols = 8;
		        numRows = 2;
		        boxWidth = theAppProfile.theWidth/numCols;
		        boxHeight = 20f;
		        spawnBoxes(curLevel);
		        
			    break;
			    
			case 3:
				//// double hit blocks
				/// set the location randomly, but make sure it's below the blocks!
				location = new PVector(pApp.random(theAppProfile.theWidth), boxTop + numRows*boxHeight + boxHeight);
		        velocity = new PVector(pApp.random(-1.1f,5.1f),pApp.random(-0.1f,0.1f));
		        acceleration = new PVector(0.1f,0.2f);
		        /// setup boxes
		        numCols = 5;
		        numRows = 2;
		        boxWidth = 204.8f; //theAppProfile.theWidth/numCols;
		        boxHeight = 60f;
		        spawnBoxes(curLevel);
		        
				
				break;
				
			case 4:
				
				/// set the location randomly, but make sure it's below the blocks!
				location = new PVector(pApp.random(theAppProfile.theWidth), boxTop + numRows*boxHeight);
		        velocity = new PVector(pApp.random(-1.1f,5.1f),pApp.random(-0.1f,0.1f));
		        acceleration = new PVector(0.1f,0.2f);
		        /// setup boxes
		      /// setup boxes
		        numCols = 8;
		        numRows = 2;
		        boxWidth = theAppProfile.theWidth/numCols;
		        boxHeight = 20f;
		        spawnBoxes(curLevel);

				break;
			case 5:
				
				break;
				
			default:
				
				break;
				
		}
			
		
		
	}
	
	////// THIS IS CALLED WHEN LEVEL FINISHING PARAMETERS ARE MET ////////
	private void endLevel(){
		
		pApp.println("END LEVEL");
		switch(curLevel){
		
		
		case 1:
			gamePaused = true;
        	/// show message
        	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "Great job! You have passed level one... but that was the easy part.\n\nNow you have to break a whole lot of boxes!", null, 255, 255);
        	/// do next level
        	curLevel = 2;
			break;
			
		case 2:
			gamePaused = true;
        	/// show message
        	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "Great job! You have passed level two... obviously this game presents very little challenge for you.\n\nFor the next level, each box has to be hit two times!", null, 255, 255);
        	/// do next level
        	curLevel = 3;
			break;
			
		case 3:
			
			gamePaused = true;
        	/// show message
        	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "Impressive! You have passed level three. Obviously these challenges are far too easy.\n\nNow you have to break a whole lot of boxes, and each one has to be broken twice!!", null, 255, 255);
        	/// do next level
        	curLevel = 4;
			break;
		
		}
		
	}

    /////// SPAWN BOXES //////////////
    private void spawnBoxes(int tLevel){
    	BreakBoxes.clear();
    	/// boxWidth = theAppProfile.theWidth/numCols;
    	/// boxHeight = 20f;
    	int numH = 1;

		/// add level specific params
		if(curLevel == 3){
			boxColor = pApp.color(165,165,165, 165);
			numH = 2;
		} else {
			boxColor = pApp.color(255, 0, 0,165);
		}
		
		
    	
    	for (int i = 0; i< numRows; i++){
    		
    		for(int j = 0; j<numCols; j++){
    			
    			
    			BreakoutBox tBox = new BreakoutBox(j * boxWidth, (i* boxHeight) + boxTop, 0.0f, boxWidth, boxHeight, boxColor);
    			tBox.numHits = numH;

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
	        /// acceleration.limit(2);
	        
	        velocity.limit(18);
	        
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
	        
	        
	        
	        //// check to see if we've hit all the blocks
	        boolean isDone = true;
	    	for(int k = 0; k< BreakBoxes.size(); k++){
	    		BreakoutBox tBox = BreakBoxes.get(k);
	    		/// if one box is not hit
	    		/// then the level is not done
	    		if(tBox.isHit == false){
	    			isDone = false;
	    		} 
	    	}
	    	
	    	if(isDone==true){
	    		if(curLevel ==1){
	    			/// do cheevo

	    			if(hasDestroyer == false){
						
						launchCheevo(null, "Destroyer!");
	    				hasBreaking = true;
					}
					
					
	    			/// 
	    		}
	    		endLevel();
	    	}
        
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
        	if( hasFirstBounce ==false){
        		launchCheevo(null, "First Bounce");
            	hasFirstBounce = true;
        		
        	}
        	
            velocity.y *= -1;
            location.y = currentFloor;
            // doImpactSounds();

            impactCounter = 255;
            hasDamage = true;
            theSoundControl.playStarWarsSound(4);
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
    				// boxTop
    				if(location.y < boxTop + tBox.theHeight/2 && hasBreaking == false){
    					
    					launchCheevo(null, "Breaking Bad");
        				hasBreaking = true;
    				}
    				
    				
    			}
    			if(location.y < tBox.theY + tBox.theHeight){
    				velocity.y *= -1.75;
    				
    			}
    			/// do the hit animation
    			tBox.doHit();
    		}	
    	}
    	
    	//// have you hit all the blocks?
    	
    	///*
    	
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
    
    
    
    ///////////////////////////
    ////// listeners //////////
    ///////////////////////////
    public void addMyEventListener(MyEventListener listener) {
        listenerList.add(MyEventListener.class, listener);
      }
      public void removeMyEventListener(MyEventListener listener) {
        listenerList.remove(MyEventListener.class, listener);
      }
      void launchCheevo(MyEvent evt, String tCheev) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == MyEventListener.class) {
            ((MyEventListener) listeners[i+1]).myEventOccurred(evt, tCheev);
          }
        }
      }

}
/// end breakout class
