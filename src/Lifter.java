import processing.core.*;


class Lifter{
	
	PApplet pApp;
	AppProfile theAppProfile;
	/// player profile
	PlayerProfile thePlayerProfile;
	
	SoundControl theSoundControl;
	
	PVector theLifter;

    float mass;
    PVector location;
    float G;

    
    float friction = .01f; /// this is the slowdown for the movement
    

	Lifter(float theG){
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;
    	
    	theSoundControl = theSoundControl.getInstance();
    	
    	/// player profile
    	thePlayerProfile = thePlayerProfile.getInstance();
    	
        location = new PVector(theAppProfile.theWidth/2, theAppProfile.theHeight/2);
        
        /// the floor's position is the current position X and the height of the screen Y
        /// theFloor = new PVector(location.x, theAppProfile.theHeight);
        /// this gives the feather gravity
        
        theLifter = new PVector(location.x,location.y);
        mass = 25;
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
    	
    	/// moves away or towards lifter
        PVector force = PVector.mult(location, f.location);
        
        float distance = force.mag();

        distance = pApp.constrain(distance, 5.0f, 25.0f);
        
        float newG;
        
        
        if(theAppProfile.curNumFingers >0 && location.x > f.location.x -50 && location.x < f.location.x + 50 && location.y > f.location.y -50 && location.y < f.location.y + 50){
        	
        	/// if so, find the side it's hitting and
        	/// bounce it in the other direction
        	if(location.x > f.location.x -50){
        		f.velocity.x *= -1; 
        	}
        	/// move it in the other direction
        	if(location.x < f.location.x + 50){
        		f.velocity.x *= 1; 
        	}
        	
        	
        	/// if it's hitting the bottom or top
        	/// then move it laterally depending on
        	/// what side it's hit on
        	if(location.y > f.location.y -50){
        		f.velocity.y *= -1; 
        		
        		/// lateral mvt
        		if(location.x < f.location.x){
        			f.velocity.x *= 1.25;
        			/// pApp.println("Bounce Right");
        		}
        		if(location.x > f.location.x){
        			f.velocity.x *= -1.5;
        			/// pApp.println("Bounce Left");
        		}
        	}
        	/// move it in the other direction
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
        	
        	
        	f.hasImpact = true;
        	f.doImpactColor();


        	thePlayerProfile.GameStats.get(theAppProfile.gameID).curScore += 123;
      	  
      	   
        } else {
        	f.hasImpact = false;
        }
        

        force.normalize();
        float strength = (G * mass * f.mass) / (distance * distance);
        force.mult(strength);
        return force;
    } 
    

    //
    void display(){

    	// pApp.noFill();
    	pApp.fill(0,0,255);
    	pApp.strokeWeight(1);
    	pApp.stroke(255,200);
    	pApp.ellipse(location.x, location.y, location.z/5, location.z/5);

    	
    	
    }
    
    
}


//// end attractor class
