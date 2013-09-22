import processing.core.*;



class Paddle{
	
	PApplet pApp;
	AppProfile theAppProfile;
	
	SoundControl theSoundControl;
	
	PImage paddleImg;
	String paddlePath = "data/paddle_base_resized.png";
	
	PVector theFloor;

    float mass;
    PVector location;
    float G;
    
    int paddleWidth;
    int paddleHeight;
    
    Poly paddleHitState;
    
    boolean isHit = false;
    
    int paddleY;
    
    float friction = .01f; /// this is the slowdown for the movement
    
    //
	
	/// particles
	ParticleSystem ps;
    
	Paddle(float theG){
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;
    	
        paddleWidth = 238;
        paddleHeight = 36;
    	paddleY = theAppProfile.theHeight - paddleHeight*2;
    	
    	float[] x = { 20, 40, 40, 60, 60, 20 };
    	float[] y = { 20, 20, 40, 40, 60, 60 };
    	
    	
    	paddleHitState = new Poly(x, y,4);
    	
    	theSoundControl = theSoundControl.getInstance();
    	
        location = new PVector(theAppProfile.theWidth/2, theAppProfile.theHeight/2);
        
        /// the floor's position is the current position X and the height of the screen Y
        theFloor = new PVector(location.x, theAppProfile.theHeight);
        mass = 180;
        G = theG;
        //
        /////// GFX ////////
        paddleImg = pApp.loadImage(paddlePath);
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
    PVector repulse(Breakout m){
    	
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
        
        //// MAKE A HIT BOX AND REPOSITION //////
        /*
    	float[] x = { location.x - paddleWidth/2, location.x + paddleWidth/2, location.x + paddleWidth/2, location.x-paddleWidth/2};
    	float[] y = { paddleY, paddleY, paddleY + paddleHeight, paddleY + paddleHeight};
        
        paddleHitState.drawPoly(x,y,4);
        /// let's see the paddle make a hit state
        if (paddleHitState.pApp.contains((int)m.location.x, (int)m.location.y)) {
        	
        	isHit = true;
        	pApp.println("HIT");
            pApp.fill(255, 165, 165);
            
          } else {
        	  
        	isHit = false;
            pApp.fill(0);
          }
        
        */
        //// 

        
        /// let's try drawing a line
        if(theAppProfile.curNumFingers >0){
        	 pApp.stroke(125);
             pApp.line(m.location.x, m.location.y, location.x, location.y);
        }
       
        if(location.x + paddleWidth/2 > m.location.x -50 && location.x - paddleWidth/2 < m.location.x + 50 && paddleY > m.location.y -50 && paddleY < m.location.y + 50){
        
        /// if (paddleHitState.contains((int)m.location.x, (int)m.location.y)) {
        // if(isHit){
        	
        	/// if so, find the side it's hitting and
        	/// bounce it in the other direction
        	
        	
        	// to do--redo bounces
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
        	if(paddleY > m.location.y -50){
        		m.velocity.y *= -1; 
        		
        		/// lateral mvt
        		if(location.x + paddleWidth/2 > m.location.x){
        			m.velocity.x *= 1.25;
        			/// pApp.println("Bounce Right");
        		}
        		if(location.x - paddleWidth/2 < m.location.x){
        			m.velocity.x *= -1.5;
        			/// pApp.println("Bounce Left");
        		}
        	}
        	/// move it in the other direction
        	if(paddleY < m.location.y + 50){
        		m.velocity.y *= 1; 
        		
        		/// lateral mvt
        		if(location.x + paddleWidth/2< m.location.x){
        			/// pApp.println("On top Bounce Right");
        			m.velocity.x *= 1.25;
        		}
        		if(location.x - paddleWidth/2> m.location.x){
        			m.velocity.x *= -1.25;
        			/// pApp.println("On Top Bounce Left");
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
        	
        	///// do particles
        	int theRnd = (int)pApp.random(50);
        	for (int i=0; i<theRnd; i++){
        		ps.origin = new PVector(location.x, paddleY);
	        	ps.addParticle();
        	
        	}
      	  
      	   
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
    	
    	// pApp.noFill();
    	pApp.fill(255,165,0,165);
    	pApp.strokeWeight(0);
    	pApp.stroke(255,200);
    	pApp.rect((location.x-paddleWidth/2), paddleY, paddleWidth - 28, paddleHeight);

    	/// add paddle image
    	pApp.image(paddleImg, (location.x-paddleWidth/2) -14, paddleY);
    	//// this updates the particles
    	ps.run();
    }
    
}



//// end paddle class
