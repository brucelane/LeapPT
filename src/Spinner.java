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
    
    int theColor;
    int theR = 0;
    int theG = 0;
    int theB = 255;
    int theA = 165;
    
    float theSpin = 0.01f;

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
        velocity = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
        acceleration = new PVector(0.1f,0.2f);
        
    }
    
    /// force
    void applyForce(PVector force){
        PVector f = PVector.div(force,mass);
        acceleration.add(f);
        
    }
    
    void update(){
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
        
        if(theR > 0){
        	theR -=10;

        }
        if(theG > 0){
        	theG -=10;

        }
        if(theB < 255){
        	theB +=10;

        }
        if(theA > 165){
        	theA -=10;

        } 
        
        theColor = pApp.color(theR,theG,theB,theA);
    }
    
    
   
    void doImpactColor(){
    	pApp.println("IMPACT COLOR");
        theR = 12;
        theG = 255;
        theB = 12;
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
        pApp.rotateY(pApp.map(location.y, 0,600, 0.5f, -0.5f));
        pApp.rotateX(pApp.map(location.x, 0,600, 1.9f, -1.9f));
        if(hasImpact){
        	theSpin +=2.1;
            pApp.rotateZ(theSpin);
        } else {
        	theSpin = .001f;
        }
        
        pApp.box(200);
        // pApp.sphere(50);
        pApp.popMatrix();

    }
    
}
/// end mover class
