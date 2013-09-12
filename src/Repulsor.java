import processing.core.*;


class Repulsor{
	
	PApplet pApp;
	AppProfile theAppProfile;
	
	SoundControl theSoundControl;
	
	PVector theFloor;

    float mass;
    PVector location;
    float G;
    
    float friction = .01f; /// this is the slowdown for the movement
    
    //
	
	/// particles
	ParticleSystem ps;
    
	Repulsor(float theG){
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;
    	
    	theSoundControl = theSoundControl.getInstance();
    	
        location = new PVector(theAppProfile.theWidth/2, theAppProfile.theHeight/2);
        
        /// the floor's position is the current position X and the height of the screen Y
        theFloor = new PVector(location.x, theAppProfile.theHeight);
        mass = 180;
        G = theG;
        
        //
        /// particles
	  	ps = new ParticleSystem(new PVector(theAppProfile.theWidth/2,50));
	  	ps.addParticle();
	  	//*/
    }
    
    public void update(float tX, float tY, float tZ){
    	
    	/// move the location to the
    	/// correct z-index

    	location = new PVector(tX, tY -friction, tZ);

    	// location = new PVector(theAppProfile.theWidth/2, theAppProfile.theHeight/2);
    }
    // returns it attraction
    PVector repulse(Bouncer m){
    	
    	/// moves towards attractor
       //  PVector force = PVector.sub(location, m.location);
        
    	/// update the floor position
    	/// currently UNDER the location
    	/// but I should make it under where
    	/// the location is going!
    	theFloor.x = m.location.x;
    	
        /// moves towards the floor
        PVector force = PVector.sub(theFloor, m.location);
        
        float distance = force.mag();
        
        //// check to see if the bouncer is hitting the repulsor
        ///  and that it's attached to fingers
        
        /// let's try drawing a line
        pApp.stroke(255);
        pApp.line(m.location.x, m.location.y, location.x, location.y);
        
        
        if(theAppProfile.curNumFingers >0 && location.x > m.location.x -50 && location.x < m.location.x + 50 && location.y > m.location.y -50 && location.y < m.location.y + 50){
        	
        	/// if so, find the side it's hitting and
        	/// bounce it in the other direction
        	if(location.x > m.location.x -50){
        		m.velocity.x *= -1; 
        	}
        	/// move it in the other direction
        	if(location.x < m.location.x + 50){
        		m.velocity.x *= 1; 
        	}
        	
        	
        	/// if it's hitting the bottom or top
        	/// then move it laterally depending on
        	/// what side it's hit on
        	if(location.y > m.location.y -50){
        		m.velocity.y *= -1; 
        		
        		/// lateral mvt
        		if(location.x < m.location.x){
        			m.velocity.x *= 1.25;
        			pApp.println("Bounce Right");
        		}
        		if(location.x > m.location.x){
        			m.velocity.x *= -1.5;
        			pApp.println("Bounce Left");
        		}
        	}
        	/// move it in the other direction
        	if(location.y < m.location.y + 50){
        		m.velocity.y *= 1; 
        		
        		/// lateral mvt
        		if(location.x < m.location.x){
        			pApp.println("On top Bounce Right");
        			m.velocity.x *= 1.25;
        		}
        		if(location.x > m.location.x){
        			m.velocity.x *= -1.25;
        			pApp.println("On Top Bounce Left");
        		}
        	}
        	
        	
        	m.hasImpact = true;
        	/*
        	m.velocity = new PVector(0.15f,0.15f);
        	m.acceleration = new PVector(0.15f,0.15f);
        	*/
        	m.doImpactColor();
        	m.doBoxHitSounds();
        	theAppProfile.scoredata += 123;
        	
        	/// update particle system
        	ps.origin = location;
        	ps.addParticle();
      	  
      	   
        } else {
        	m.hasImpact = false;
        }
        
        //// this tells us if the velocity between 
        //// the attractor and the mover is less than 20
        if(distance < 20){
        	/// do lock


        } 

        distance = pApp.constrain(distance, 5.0f, 25.0f);
        
        float newG;
       
        force.normalize();
        float strength = (G * mass * m.mass) / (distance * distance);
        force.mult(strength);
        return force;
    } 
    

    //
    void display(){

    	/// pApp.pushMatrix();
    	/// pApp.translate(0,0,300);
 	    
    	// pApp.noFill();
    	pApp.fill(0,255,0,165);
    	pApp.strokeWeight(1);
    	pApp.stroke(255,200);
    	pApp.ellipse(location.x, location.y, location.z/5, location.z/5);

    	/// pApp.popMatrix();
    	
    	ps.run();
    }
    
}


//// end attractor class
