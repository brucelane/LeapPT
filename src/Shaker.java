import processing.core.*;


class Shaker{
	
	PApplet pApp;
	AppProfile theAppProfile;
	
	SoundControl theSoundControl;
	
	Checkerboard theCheckers;
	
	PVector theFloor;

    float mass;
    PVector location;
    float G;
    
    float friction = .01f; /// this is the slowdown for the movement
    
    //
	
	/// particles
	ParticleSystem ps;
    
	Shaker(float theG){
    	
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
    	location = new PVector(tX, tY, tZ);

    }
    // returns it attraction
    PVector spin(Spinner m){
        PVector force = PVector.sub(location, m.location);
        float distance = force.mag();

        //// check to see if the attractor is overlapping the mover
        ///  and that there are fingers attached to the attractor
        if(theAppProfile.curNumFingers >0 && location.x > m.location.x -50 && location.x < m.location.x + 50 && location.y > m.location.y -50 && location.y < m.location.y + 50){
        	
        	m.hasImpact = true;
        	
        	m.velocity = new PVector(0.15f,0.15f);
        	m.acceleration = new PVector(0.15f,0.15f);
        	
        	m.doImpactColor();
        	m.doBoxHitSounds();
        	theAppProfile.scoredata += 123;
        	
        	/// update particle system
        	ps.origin = location;
        	ps.addParticle();
      	  
      	   
        } else {
        	m.hasImpact = false;
        }


        distance = pApp.constrain(distance, 5.0f, 25.0f);

        force.normalize();
        float strength = (G * mass * m.mass) / (distance * distance);
        force.mult(strength);
        return force;
    } 
    
    //
    void display(){

    	/*
    	pApp.pushMatrix();
    	pApp.translate(0,0,300);
 	    
    	// pApp.noFill();
    	pApp.fill(0,0,255,165);
    	pApp.strokeWeight(1);
    	pApp.stroke(255,200);
    	pApp.ellipse(location.x, location.y, location.z/5, location.z/5);
    	
    	pApp.popMatrix();
    	
    	ps.run();
    	*/
    }
    
    
    public void doImpact(float tX, float tY, float tZ){
    	pApp.println(tX + " " + tY + " " + tZ);
    	
    	
    	location.x = tX;
    	location.y = tY;
    	location.z = tZ;
    	
    }
    
   

    
}


//// end attractor class
