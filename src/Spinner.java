import processing.core.*;


import processing.opengl.PGraphicsOpenGL;


class Spinner{
	
	PApplet pApp;
	AppProfile theAppProfile;
	  
	SoundControl theSoundControl;
	  
    PVector location;
    PVector velocity;
    PVector acceleration;
    PVector wind;
    PVector gravity;
    PVector friction;
    
    int theColor;
    int theR = 0;
    int theG = 0;
    int theB = 255;
    int theA = 165;
    
    float baseRotX = .01f;
    float baseRotY = .01f;
    float baseRotZ = .01f;
    
    
    float curRotX = .5f;
    float curRotY = .5f;
    float curRotZ = .5f;
    
    float theSpin = 0.01f;
    float baseSpin = 0.01f;

    float mass;
    int ballSize = 35;
    
    boolean hasImpact = false;
    
    /// the mass, the x, the y);
    Spinner(float m, float x, float y){
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;

    	theSoundControl = theSoundControl.getInstance();
    	 // = new SoundControl();
    	
    	theColor = pApp.color(theR,theG,theB,theA);
    	
        mass = m;
        /// this always starts in the middle of the screen
        location = new PVector(theAppProfile.theWidth/2, theAppProfile.theHeight/2);
        velocity = new PVector(baseRotX, baseRotY, baseRotZ);
        acceleration = new PVector(0.1f,0.2f);
        friction = new PVector(0.01f, 0.01f, 0.01f);
        
    }
    
    /// force
    void applyForce(PVector force){
        PVector f = PVector.div(force,mass);
        acceleration.add(f);
        
    }
    
    void update(){
    	
    	///mvt
    	/*
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
        */
        
        // normalize rotation
        
    	if(baseRotX < velocity.x){
    		velocity.x -= friction.x;

    	} else {
    		velocity.x = baseRotX;
    	}
        
    	if(baseRotY < velocity.y){
    		velocity.y -= friction.y;

    	} else {
    		velocity.y = baseRotY;
    	}
        
    	if(baseRotZ < velocity.z){
    		if(velocity.z >100){
    			velocity.z -= 100;
    		}
    		if(velocity.z > 10){
    			velocity.z -= 10;
    		}
    		if(velocity.z >1){
    			velocity.z -= 1;
    		}
    		velocity.z -= friction.z;

    	} else {
    		velocity.z = baseRotZ;
    	}
    	
    	
        
        if(theR > 0){
        	theR -=5;

        }
        if(theG > 0){
        	theG -=5;

        }
        if(theB < 255){
        	theB +=5;

        }
        if(theA > 165){
        	theA -=5;

        } 
        
        theColor = pApp.color(theR,theG,theB,theA);
    }
    
    public void doImpact(float tX, float tY, float tZ){
    	hasImpact = true;
    	doImpactColor();
    	doBoxHitSounds();
    	

    	velocity.x = tX;
    	velocity.y = tY;
    	velocity.z = tZ;
    	
    	// pApp.println("SPINNER IMPACT: " + velocity.x + " : " + velocity.y + " : " + velocity.z);
    }
   
    void doImpactColor(){

        theR = 255;
        theG = 255;
        theB = 255;
        theA = 255;
        
        
    }

    void doImpactSounds(){
    	
    	/// switch
       
	   int theRnd = (int)pApp.random(4);
	   /// pApp.println("wall hit: " + theRnd);
	   theSoundControl.playStarWarsSound(theRnd);

    }
    
    void doBoxHitSounds(){
       int theRnd = 4 + (int)pApp.random(7);
   	   theSoundControl.playStarWarsSound(theRnd);
   	   /// pApp.println(theRnd);
    }
    
    
    void display(){
    	
        pApp.stroke(255,0,0);
        pApp.strokeWeight(1);
        pApp.fill(theColor);

        pApp.pushMatrix();
        pApp.translate(location.x, location.y, location.z); /// x,y and z?
        
      /// add the x and y position to the movement
        /*
        curRotY += .01;
        curRotX += .01;
        curRotZ += theSpin;
        */
        
        pApp.rotateY(curRotX += velocity.x);
        pApp.rotateX(curRotY += velocity.y);
    	pApp.rotateZ(curRotZ += velocity.z);
        
        pApp.box(200);
        // pApp.sphere(50);
        pApp.popMatrix();

    }
    
}
/// end mover class
