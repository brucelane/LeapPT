import processing.core.*;


class Lifter{
	
	PApplet pApp;
	AppProfile theAppProfile;
	/// player profile
	PlayerProfile thePlayerProfile;
	
	SoundControl theSoundControl;
	
	PVector theFloor;

    float mass;
    PVector location;
    float G;
    

    float friction = .5f; /// this is the slowdown for the movement
    

	Lifter(float theG){
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;
    	
    	theSoundControl = theSoundControl.getInstance();
    	
    	/// player profile
    	thePlayerProfile = thePlayerProfile.getInstance();
    	
        location = new PVector(theAppProfile.theWidth/2, theAppProfile.theHeight/2);
        
        /// the floor's position is the current position X and the height of the screen Y
        theFloor = new PVector(location.x, theAppProfile.theHeight);
        mass = 40;
        G = theG;
      
       
    }
    
    public void update(float tX, float tY, float tZ){
    	
    	/// move the location to the
    	/// correct z-index

    	location = new PVector(tX, tY -friction, tZ);

    	// location = new PVector(theAppProfile.theWidth/2, theAppProfile.theHeight/2);
    }
    // returns it attraction
    PVector repulse(Feather f){
    	
    	/// moves towards attractor
       //  PVector force = PVector.sub(location, m.location);
        
    	/// update the floor position
    	/// currently UNDER the location
    	/// but I should make it under where
    	/// the location is going!
    	theFloor.x = f.location.x;
    	
        /// moves towards the floor
        PVector force = PVector.mult(theFloor, f.location);
        
        
        f.addLift(location.y);
        f.addSpin(location.x);
        
        
        float distance = force.mag();
        
        //// check to see if the bouncer is hitting the repulsor
        ///  and that it's attached to fingers
        
        /// let's try drawing a line
    	
        /*
        if(theAppProfile.curNumFingers >0){
        	
        	 //  pApp.line(f.location.x + (location.x/f.location.x), f.location.y + (location.y/f.location.y), location.x, location.y);
        	
             f.drawLine(f.location.x, f.location.y, location.x, location.y);
        }
       */
        
        if(theAppProfile.curNumFingers >0 && location.x > f.location.x -50 && location.x < f.location.x + 50 && location.y > f.location.y -50 && location.y < f.location.y + 50){
        	
        	/// if so, find the side it's hitting and
        	/// bounce it in the other direction
        	/* NO SIDE HITTING 
        	if(location.x > f.location.x -50){
        		f.velocity.x *= -1; 
        	}
        	/// move it in the other direction
        	if(location.x < f.location.x + 50){
        		f.velocity.x *= 1; 
        	}
        	
        	*/
        	/// if it's hitting the bottom 
        	/// then move it laterally depending on
        	/// what side it's hit on
        	if(location.y > f.location.y){
        		
        		/// maybe save this for harder levels?
        		// f.velocity.y *= -.025; 
        		
        		/// lateral mvt
        		if(location.x < f.location.x){
        			f.velocity.x *= 1.5;
        			/// pApp.println("Bounce Right");
        		}
        		if(location.x > f.location.x + 50){
        			f.velocity.x *= -1.5;
        			/// pApp.println("Bounce Left");
        		}
        	}
        	/// no top hits
        	/*
        	if(location.y < f.location.y + 50){

        		f.velocity.y *= 1; 
        		
        		/// lateral mvt
        		if(location.x < f.location.x){
        			/// pApp.println("On top Bounce Right");
        			f.velocity.x *= 1.25;
        		}
        		if(location.x > f.location.x){
        			f.velocity.x *= -1.25;
        			/// pApp.println("On Top Bounce Left");
        		}
        		
        		
        	}
        	*/
        	
        	f.hasImpact = true;
        	f.doImpactColor();
        	
        	thePlayerProfile.GameStats.get(theAppProfile.gameID).curScore += 123;
      	  
      	   
        } else {
        	f.hasImpact = false;
        }
        
        
        distance = pApp.constrain(distance, 5.0f, 25.0f);
        
        float newG;
       
        force.normalize();
        float strength = (G * mass * f.mass) / (distance * distance);
        force.mult(strength);
        return force;
    } 
    

    //
    void display(){


    	// pApp.fill(0,0,255,165);
    	pApp.strokeWeight(1);
    	pApp.stroke(255);
    	pApp.fill(0,0,255,165);
    	pApp.ellipse(location.x, location.y, location.z/5, location.z/5);


    }
      
}//// end lifter class
