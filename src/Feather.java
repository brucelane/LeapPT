import processing.core.*;

import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;


class Feather{
	
	PApplet pApp;
	AppProfile theAppProfile;
	/// player profile
	PlayerProfile thePlayerProfile;
	  
	SoundControl theSoundControl;
	  
    PVector location;
    PVector velocity;
    PVector acceleration;
    PVector wind;
    PVector gravity;
    PVector gravityBase;
    
	PFont timerFont;

    /// modifiers
    float windModifier;
    
    PVector spin;
    
    float theSpin = 0.01f;
    boolean spinDir = false;
    
    /// colors 
    int theColor;
    int theR = 255;
    int theG = 164;
    int theB = 0;
    int theA = 255;
    
    int crossHairColor = 255;
    int crossHarModClr = 255;
    
    int blueTransp;
    
    /// crosshair vars
    float crossCntrX;
    float crossCntrY;
    float crossLine;
    
   
    int curLevel;
    int curHits;
    int respawnOrigin;

    float mass;
    int ballSize = 35;
    
    boolean hasImpact = false;
    boolean gamePaused = true;
    boolean firstPlayed = true;
    boolean isVisible = true;
    
    int impactCounter;
    
    PImage theTexture;
    String texturePath = "data/games/feather_fat.png";
    
    PImage bgroundImg;
    String theBgroundImgPath = "data/backgrounds/featherweight_bg.png";

    /// game messages
    GameMessaging thePopup;
    /// timers
    TimerClass theTimer;
    // listeners
    protected EventListenerList listenerList = new EventListenerList();
    /// the mass, the x, the y);
    
    
    Feather(float m){
    	mass = m;
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;

    	thePlayerProfile = thePlayerProfile.getInstance();
    	theSoundControl = theSoundControl.getInstance();
        thePopup = thePopup.getInstance();
    	 // = new SoundControl();
    	
    	theColor = pApp.color(theR,theG,theB,theA);

        theTexture = pApp.loadImage(texturePath);
        bgroundImg = pApp.loadImage(theBgroundImgPath);
        
		theTimer = new TimerClass();
		
		timerFont = pApp.createFont("Gotham Rounded",22, true); /// normal fonts
		
		blueTransp = pApp.color(0,0,255,65);
		
		crossCntrX = theAppProfile.theWidth/2;
		crossCntrY = theAppProfile.theHeight/2;	
		crossLine = 60*5; /// length of the crosshairs
		    
    	
    	curLevel = 1;
    }
    
    public void startNewGame(){

    	 /// do initial messags
    	thePopup.initMessage(0, 0, "WELCOME TO FEATHERWEIGHT!", "This game is designed to strenghten your extenders by opening, closing, and lifting your hand. First, we'll start off easy-- raise your hand to make the feather rise to the top of the screen five times!", "data/games/gfx_fw_stage1.png",  255, 255);
        setLevelParams();
    	
    }
    
    
    //////// THIS IS CALLED FROM THE DIALOG CLOSE BUTTON
    //////// YOU NEVER HIT THIS UNLESS YOU"RE STARTING A NEW LEVEL
    public void doNextLevel(){
    	
    	switch (curLevel){
		
    	case 0:
    		
    		break;
        ///////// finger level
    	case 1:
    		
    		break;
    		
    	//////// hand level	
		case 2:
		    		
		    break;
		case 3:
			//// TIMER level
			theTimer.stop();
			theTimer.start();
			break;
			
		case 4:
			//// hover level
			theTimer.stop();
			theTimer.start();
			break;
		case 5:
			
			break;
    		
    	default:
    		
    		break;
    		
    	}
    	
    	gamePaused = false;
    	
    }
    
    ////// SET FEATHER MOVEMENT DEPENDING ON LEVEL ///////
    private void setLevelParams(){
        
    	
    	/// change params depending on 
    	/// what level it is (NO LEVEL 0)
    	switch (curLevel){
    		
    	/////// SINGLE FINGER LEVEL
	    	case 1:
	    		respawnOrigin = theAppProfile.theHeight - theTexture.height;
		        location = new PVector(theAppProfile.theWidth/2, respawnOrigin);
		        velocity = new PVector(0,pApp.random(0,1.50f));
		        acceleration = new PVector(0.0f,0.12f);
		        gravityBase = new PVector(0, 0.0225f);
		        gravity = new PVector(gravityBase.x, gravityBase.y);
		        ////
		        wind = new PVector(0, 0.0f);
		        spin = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
		        /// modifiers
		   		windModifier = .05f;

		        break;
		  
		  //////// WHOLE HAND LEVEL
	    	case 2:
	    		respawnOrigin = theAppProfile.theHeight - theTexture.height;
	    		location = new PVector(theAppProfile.theWidth/2, respawnOrigin);
		        velocity = new PVector(0,pApp.random(0,1.50f));
		        acceleration = new PVector(0.0f,0.12f);
		        gravityBase = new PVector(0, 0.03f);
		        gravity = new PVector(gravityBase.x, gravityBase.y);
		        ////
		        wind = new PVector(0, 0.0f);
		        spin = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
		        /// modifiers
		        windModifier = .003f; // each level the modifier is less
		        
	    		break;
	    ///////////// TIMER LEVEL
	    	case 3:
	    		respawnOrigin = theAppProfile.theHeight - theTexture.height;
	    		location = new PVector(theAppProfile.theWidth/2, respawnOrigin);
		        velocity = new PVector(0,pApp.random(0,1.50f));
		        acceleration = new PVector(0.0f,0.12f);
		        gravityBase = new PVector(0, 0.04f);
		        gravity = new PVector(gravityBase.x, gravityBase.y);
		        ////
		        wind = new PVector(0, 0.0f);
		        spin = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
		        /// modifiers
		        windModifier = .001f; // each level the modifier is less
	    		
	    		break;
	    		
	    ////////// HOVER LEVEL	
	    	case 4:
	    		respawnOrigin = theAppProfile.theHeight - theTexture.height;
	    		location = new PVector(theAppProfile.theWidth/2, respawnOrigin);
		        velocity = new PVector(0,pApp.random(0,1.50f));
		        acceleration = new PVector(0.0f,0.12f);
		        gravityBase = new PVector(0, 0.05f);
		        gravity = new PVector(gravityBase.x, gravityBase.y);
		        ////
		        wind = new PVector(0, 0.0f);
		        spin = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
		        /// modifiers
		        windModifier = .001f; // each level the modifier is less
		        
	    		break;
	    		
	    		
	    	case 5:
	    		respawnOrigin = theAppProfile.theHeight - theTexture.height;
	    		location = new PVector(theAppProfile.theWidth/2, respawnOrigin);
		        velocity = new PVector(0,pApp.random(0,1.50f));
		        acceleration = new PVector(0.0f,0.12f);
		        gravityBase = new PVector(0, 0.0175f);
		        gravity = new PVector(gravityBase.x, gravityBase.y);
		        ////
		        wind = new PVector(0, 0.0f);
		        spin = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
		        /// modifiers
		        windModifier = .0035f; // each level the modifier is less
		        
	    		break;
	        
	        
	    	default:
	    		respawnOrigin = theAppProfile.theHeight - theTexture.height;
	    		location = new PVector(theAppProfile.theWidth/2, respawnOrigin);
		        velocity = new PVector(0,pApp.random(0,1.50f));
		        acceleration = new PVector(0.0f,0.12f);
		        gravityBase = new PVector(0, 0.0175f);
		        gravity = new PVector(gravityBase.x, gravityBase.y);
		        ////
		        wind = new PVector(0, 0.0f);
		        spin = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
		        /// modifiers
		        windModifier = .0035f; // each level the modifier is less
		        
	    		break;
    	}
        
    	
    }
    
    /// SET EDGE COLLISION EVENTS DEPENDING ON LEVEL ////////
    public void checkEdges(){
    	
    	
    	/// change params depending on 
    	/// what level it is
    	switch (curLevel){
    	
    	/////// BASE LEVEL
    	case 1:
    		
    		/// why am I looking at x positions?
	        if(location.x>theAppProfile.theWidth){
	            location.x = theAppProfile.theWidth;
	            velocity.x *= -.65;
	            
	            theAppProfile.scoredata += 23;
	
	        } else if (location.x<0){
	            velocity.x *= -.65;
	            location.x = 0;
	           
	            theAppProfile.scoredata += 23;
	            
	        }
	        ////// BOTTOM HIT
	        if(location.y>theAppProfile.theHeight){

	        	//// do particle effects and win sound
	            //// location.y = respawnOrigin;
	        	theSoundControl.playFeatherSounds(0);
	            //// setLevelParams();

	            //// make it bounce
	            velocity.y *= -1.25;
	        ////// TOP HIT
	        } else if (location.y< -50){
	        	theSoundControl.playFeatherSounds(1);
	            theAppProfile.scoredata += 23;
	            setLevelParams();
	            curHits +=1;
	            
	            location.y = respawnOrigin;
	            //// test for status
	            if(curHits == curLevel*5){
	            	/// launchCheevo(null, "Level One");
	            	gamePaused = true;
	            	// game state control: win, lose, new game
	            	theSoundControl.playGameStateSounds(0);
	            	/// show message
	            	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "Great job! You have passed level one. You are awesome! Let's try something a little harder-- you have to use all five fingers to make the feather rise to the top... and you have to do it ten times!", "data/games/gfx_fw_stage2.png", 255, 255);
	            	/// do next level
	            	curLevel = 2;
	            }
	        }
        
        break;
        
        
        ////////// MULTI FINGER LEVEL
    	case 2:
    		/// why am I looking at x positions?
	        if(location.x>theAppProfile.theWidth){
	            location.x = theAppProfile.theWidth;
	            velocity.x *= -.65;
	            
	            theAppProfile.scoredata += 23;
	
	        } else if (location.x<0){
	            velocity.x *= -.65;
	            location.x = 0;
	            
	            theAppProfile.scoredata += 23;
	            
	        }
	        ////// BOTTOM HIT
	        if(location.y>theAppProfile.theHeight){

	        	//// do particle effects and win sound
	            //// location.y = respawnOrigin;
	            /// doBounceSound();
	            //// setLevelParams();

	            //// make it bounce
	        	velocity.y *= -1.25;
	        ////// TOP HIT
	        } else if (location.y< -50){
	        	theSoundControl.playFeatherSounds(1);
	            theAppProfile.scoredata += 23;
	            setLevelParams();
	            curHits +=1;
	            location.y = respawnOrigin;
	            //// test for status
	            if(curHits == 15){
	            	/// launchCheevo(null, "Level One");
	            	gamePaused = true;
	            	// game state control: win, lose, new game
	            	theSoundControl.playGameStateSounds(0);
	            	/// show message
	            	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "Great job! You have passed level two... but that was the easy part.\n\nNow you have to beat the timer! Get the feather to the top of the screen before the timer runs out!", "data/games/gfx_fw_stage3.png", 255, 255);
	            	/// do next level
	            	curLevel = 3;
	            }
	        }
	        
	        break;
        
	   ///////// TIMER LEVEL //////////////////
	        
	        /*
	         * 		SoundsFeatherweight.add(featherGameStart);
		SoundsFeatherweight.add(featherRespawn);
		SoundsFeatherweight.add(featherSuccess);
		*/
    	case 3:

    		/// why am I looking at x positions?
	        if(location.x>theAppProfile.theWidth){
	            location.x = theAppProfile.theWidth;
	            velocity.x *= -.65;
	            
	            /// feather sound control respawn, success, new game
	            theSoundControl.playFeatherSounds(0);
	            theAppProfile.scoredata += 23;
	
	        } else if (location.x<0){
	            velocity.x *= -.65;
	            location.x = 0;
	            
	            theAppProfile.scoredata += 23;
	            
	        }
	        ////// BOTTOM HIT
	        if(location.y>theAppProfile.theHeight){

	        	//// do particle effects and win sound
	            //// location.y = respawnOrigin;
	            /// doBounceSound();
	            //// setLevelParams();
	        	 theSoundControl.playFeatherSounds(0);
	            //// make it bounce
	        	velocity.y *= -1.25;
	        ////// TOP HIT
	        } else if (location.y< -50){
	        	theSoundControl.playFeatherSounds(1);
	            theAppProfile.scoredata += 23;
	            setLevelParams();
	            curHits +=1;
	            location.y = respawnOrigin;
	            //// test for status
	            if(theTimer.getElapsedTime() <= 15000 ){
	            	theTimer.stop();
	            	/// launchCheevo(null, "Level One");
	            	// game state control: win, lose, new game
	            	theSoundControl.playGameStateSounds(0);
	            	gamePaused = true;
	            	/// show message
	            	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "Amazing! You beat level three, and showed great courage under pressure.\n\nNow for something completely different. You have to make the feather hover in place. Open your hand all the way to make it lift, and close into a fist to make it drop. Keep it in the crosshairs for 15 seconds!", "data/games/gfx_fw_stage4.png", 255, 255);
	            	/// do next level
	            	curLevel = 4;
	            }
	        }
    		break;
    		
    		
    		///////// HOVER LEVEL
    		
    	case 4:

	   		 /// check to make sure you're not in the crosshair
    		crossHarModClr = pApp.color(255);
    		if(location.y > (theAppProfile.theHeight/2) - crossLine/2 && location.y < (theAppProfile.theHeight/2) + crossLine/2){
    			crossHarModClr = pApp.color(0,255,0);
	   			/// pApp.println("GREENY : " + location.y + " circle y: " + crossCntrY + " circle y + height: " + tCrossWidth + " HEIGHT: " + crossLine);
		   		
	   			 
	   		 } else {
	   			crossHarModClr = pApp.color(255,0,0);
	   			/// pApp.println("REDY : " + location.y + " circle y: " + crossCntrY + " circle y + height: " + tCrossWidth + " HEIGHT: " + crossLine);
		   		theTimer.stop();
		   		theTimer.start();
	   		 }
	   		
    		
    		/// check to see if you've hovered enough
   		 	if(theTimer.getElapsedTime() >= 15000 ){
	            	theTimer.stop();
	            	/// launchCheevo(null, "Level One");
	            	gamePaused = true;
	            	/// show message
	            	thePopup.initMessage(0, 0, "LEVEL COMPLETE", "BOOM! Your powers are growing stronger, grasshopper. You have beaten level four, the final level. \n\nPerhaps that was not a sufficient test of your skill. Go to the settings panel in the main menu, and increase the difficulty... IF YOU DARE!", null,255, 255);
	            	/// do next level
	            	curLevel = 4;
	         }
    		
   		 ////// BOTTOM HIT
	        if(location.y>theAppProfile.theHeight){

	        	// wrap it
	        	location.y = respawnOrigin;
	        	
	        ////// TOP HIT
	        } else if (location.y< -50){
	        	
	        	/// reset timer
	        	
	        	/// do mistake sound
	        	theSoundControl.playFeatherSounds(0);
	            theAppProfile.scoredata += 23;
	            setLevelParams();
	            //// do a loop... maybe bounce it back?
	            location.y = theAppProfile.theHeight - 100;
	            //// test for status
	           
	        }
    		break;
    		
    	default:
    		
    		
    		break;
    		
    		
    	} // endswitch

    }
    
    
    /// force
    public void applyForce(PVector force){
    	
    	/// switching from div to mult seems to do nothing
        PVector f = PVector.div(force,mass);
        // change the direction of the spin
        // dpending on where the fingers are
        /// but leave the force the same
        
        if(spinDir == true){
        	 spin.add(f);
        } else {
        	 spin.sub(f);
        }

        /// this gives it x movement, which we don't want
        // acceleration.add(f);
        
    }
    
    public void addSpin(float theX){
    	if(theX < theAppProfile.theWidth/2){
    		
    		spinDir = false;
    		
    	} else {
    		spinDir = true;
    	}
    	/// spin.x = pApp.map(theX,  0,  theAppProfile.theWidth,  -15, 15);
    }
    
    public void addLift(float theY){
    	/// if there's a lot of fingers, ramp the antigravity down
    	
    	/// if there's not so many fingers, ramp the antigravity up
    	
    	/// at higher levels, use finger position to lessen velocity
    	/// take the amount of gravity added
    	if(theAppProfile.curNumFingers >= 4){
    		wind.y -= windModifier + .004;
    		
    	}
      	if(theAppProfile.curNumFingers == 3){
    		wind.y -= windModifier + .003;
    		
    	}
    	if(theAppProfile.curNumFingers == 2){
    		wind.y -= windModifier + .002;
    		
    	}
      	if(theAppProfile.curNumFingers == 1){
    		wind.y -= windModifier + .001;
    		
    	}

      	/// if above the feather, reduce velocity even more
    	if(theY < location.y){
    		
    		float velAdj = pApp.map(theY - location.y, theAppProfile.theHeight, 0, 0, 1)*.005f;
    		
    		velocity.y -= velAdj;
    		
    	}
    	
    	// normalize wind
    	if(wind.y<0 && theAppProfile.curNumFingers <=0){
    		
    		wind.y += .01;
    	}
    	

    }
    public void update(){
    	/// normalize gravity
    	
    	if(gamePaused == false){
    		
    		if(wind.y <= 0.0145f){
        		
        		wind.y += .02;
        		
        		/// pApp.println("Grav: " + gravity.y);
        	}
            velocity.add(acceleration);
            velocity.add(gravity);
            /// add the lift
            velocity.add(wind);
            location.add(velocity);
            
            
            /// acceleration.limit(1);
            velocity.limit(2);
            acceleration.mult(0);
            
            /*
            pApp.println("NUM FIGERS: " + theAppProfile.curNumFingers);
            pApp.println("Gravity: " + gravity.toString());
            pApp.println("Wind: " + wind.toString());
            pApp.println("Accel: " + acceleration.toString());
            pApp.println("Velocity: " + velocity.toString());
            pApp.println(" ");
            
            */
            
            if(theR < 255){
            	theR +=10;

            }
            if(theG > 164){
            	theG -=10;

            }
            if(theB > 0){
            	theB -=10;

            }

            theColor = pApp.color(theR,theG,theB,theA);
    	}
    	
    }
    
    
    
    
    void doImpactColor(){
    	/// pApp.println("IMPACT COLOR");
        theR = 0;
        theG = 255;
        theB = 0;
        theA = 255;
        
        
    }

    void doImpactSounds(){
    	
    	/// switch
       
	   int theRnd = (int)pApp.random(4);
	   /// pApp.println("wall hit: " + theRnd);
	   theSoundControl.playStarWarsSound(theRnd);

    }

    private void drawTimerDisplay(){
    	
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
		// String theTime = hour + ":" + min + ":" + sec + ":" + theTimer.milisecond(); /// String.valueOf(theAppProfile.scoredata);
		String theTime = sec + ":" + theTimer.milisecond(); /// String.valueOf(theAppProfile.scoredata);

		int tSecs = 15- theTimer.second();
		int tMils = 100 - theTimer.milisecond();
		pApp.fill(255);;
		pApp.textFont(timerFont, 22);
		// pApp.text("TIME REMAINING: \n " +  theTimer.second(), 20, theAppProfile.theHeight - 10);
		pApp.text("TIME REMAINING:  " +  tSecs + " : " + tMils, theAppProfile.theWidth/2 - 190, 120);
		
    }
    
    void display(){
    	pApp.hint(pApp.DISABLE_DEPTH_TEST);
        pApp.image(bgroundImg,0,0);
    	////////// SPECIFIC LEVEL CONFIG
    	switch(curLevel){
    	
    	
    	case 1:
    		// doCrosshair();
    		break;
    	
    	case 3:
    		
    		/// draw timer
    		drawTimerDisplay();
    		if(theTimer.getElapsedTime() >= 15000 ){

    			theTimer.stop();
    			/// show loser message
    			thePopup.initMessage(0, 0, "YOU ARE A LOSER", "How does that make you feel? No one loves you. Go eat some fritos.\n\nMaybe you should go cry in the corner.", null, 255, 255);
            	
    		 }
    		
    		break;
    		
    	case 4:
    		
    		
    		doCrosshair();
    		
    		
    		break;
    		
    		
    	default:
    		
    		
    		break;
    	
    	
    	}
    	pApp.hint(pApp.ENABLE_DEPTH_TEST);
       /// pApp.hint(pApp.DISABLE_DEPTH_TEST);
  	   pApp.pushMatrix();
  	   ///  first, upscale and reposition
  	   pApp.scale(2.5f);
       //// then move back in the z index so UI is on top
  	   pApp.translate(theAppProfile.theWidth/2-((bgroundImg.width/2) * 1.5f),theAppProfile.theHeight/2-((bgroundImg.height/2) *1.5f),-250);

       pApp.popMatrix();
        
	   pApp.pushMatrix();
       pApp.translate(location.x, location.y, -110); /// this just barely drops it behind the UI
       /// add the x and y position to the movement
       // pApp.rotateY(pApp.map(location.y, 0,600, 0.0f, 0.5f));
       
       ////// LINK THIS TO THE VELOCITY INSTEAD //////////
       
       // float rotTweak = pApp.map(location.x, 0, theAppProfile.theWidth, 15, -15);
       float rotTweak = pApp.map(spin.x, -360,360, 10, -20);
       // pApp.print("spin: " + spin.x);
       // pApp.println("\n");
       pApp.rotateZ(rotTweak); /// original value -15
       pApp.rotateX(5.5f);

       pApp.beginShape();
   	   pApp.fill(0,0,255,0);
	   pApp.noStroke();
	  	
       pApp.texture(theTexture);
       

       pApp.vertex(-150, -150, 0, 0, 0);
       pApp.vertex(150, -150, 0, theTexture.width, 0);
       pApp.vertex(150, 150, 0, theTexture.width, theTexture.height);
       pApp.vertex(-150, 150, 0, 0, theTexture.height);
       
       pApp.endShape();
       
       pApp.popMatrix();


       if(thePopup.showingMessage == true){
    	   thePopup.drawMessage();
    	   
       }
    }
    
    public void drawLine(float x, float y, float tx, float ty){
    	pApp.hint(pApp.DISABLE_DEPTH_TEST);
    	pApp.pushMatrix();
		pApp.translate(0,0,40);
    	 pApp.strokeWeight(2);
    	 pApp.stroke(0);
         pApp.line(x,y, tx, ty);
         pApp.popMatrix();
         pApp.hint(pApp.ENABLE_DEPTH_TEST);
    	
    }
    
    ////////////////////////
    ////// CROSSHAIRS ////////
    ////////////////////////
    private void doCrosshair(){

			int tSpacing = 5*5;
			pApp.strokeWeight(2);
			pApp.stroke(crossHairColor);
			pApp.pushMatrix();
			pApp.translate(0,0,10);
	
			pApp.fill(crossHairColor);
			  /// do numbers
			pApp.textFont(timerFont, 20);
			  /// do z index to match feather position
			pApp.text(theTimer.second() + " seconds", crossCntrX + crossLine/1.8f,crossCntrY -5);
			  //pApp.textFont(readoutFontM);
			float fakeNums = location.y -  crossCntrY;
			pApp.textFont(timerFont, 14);
			pApp.text(" " + theAppProfile.curNumFingers + " x number fingers", crossCntrX + crossLine/2,crossCntrY + 15);
			pApp.text(" v sync: " + fakeNums, crossCntrX + crossLine/2,crossCntrY + 30);
			  //pApp.textFont(readoutFont);
			  ///pApp.text(theLat, screenPos.x + 5,crossCntrY + 45);

			pApp.noFill();

			/// trim x
			pApp.line(crossCntrX,crossCntrY - tSpacing, crossCntrX + crossLine/2,crossCntrY -tSpacing);
			pApp.line(crossCntrX,crossCntrY + tSpacing, crossCntrX + crossLine/2,crossCntrY + tSpacing);
			
			pApp.ellipse(crossCntrX,crossCntrY, crossLine, crossLine);
			pApp.ellipse(crossCntrX,crossCntrY, crossLine*.95f, crossLine*.95f);
			// pApp.ellipse(crossCntrX,crossCntrY, crossLine*.65f, crossLine*.65f);
			/// target ellipse crossHarModClr
			pApp.fill(crossHarModClr, 165);
			pApp.ellipse(crossCntrX,crossCntrY, crossLine*.45f, crossLine*.45f);
			
			/// crosshair
			pApp.stroke(crossHarModClr, 255);
			// do horiz
			pApp.line(crossCntrX-crossLine/2,crossCntrY, crossCntrX + crossLine,crossCntrY);
			/// do vert
			pApp.line(crossCntrX,crossCntrY-crossLine/2, crossCntrX,crossCntrY + crossLine/1.75f);

			//*/
			pApp.popMatrix();
			
		

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
    
}/// end feather class
