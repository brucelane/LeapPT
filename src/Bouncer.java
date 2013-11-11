import processing.core.*;


// import processing.opengl.PGraphicsOpenGL;


class Bouncer{
	
	PApplet pApp;
	AppProfile theAppProfile;
	  
	SoundControl theSoundControl;
	  
    PVector location;
    PVector velocity;
    PVector acceleration;
    PVector wind;
    PVector gravity;
    
    int theColor;
    int theR = 255;
    int theG = 164;
    int theB = 0;
    int theA = 255;
    
    float theSpin = 0.01f;

    float mass;
    int ballSize = 35;
    
    boolean hasImpact = false;
    boolean hasDamage = false;
    
    int impactCounter;
    
    /// the mass, the x, the y);
    Bouncer(float m, float x, float y){
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;

    	theSoundControl = theSoundControl.getInstance();
    	 // = new SoundControl();
    	
    	theColor = pApp.color(theR,theG,theB,theA);
    	
        mass = m;
        location = new PVector(pApp.random(theAppProfile.theWidth), pApp.random(theAppProfile.theHeight));
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
    
    
    
    void checkEdges(){
        if(location.x>theAppProfile.theWidth){
            location.x = theAppProfile.theWidth;
            velocity.x *= -.65;
            doBounceSound();
            theAppProfile.scoredata += 23;

        } else if (location.x<0){
            velocity.x *= -.65;
            location.x = 0;
            doBounceSound();
            theAppProfile.scoredata += 23;
            
        }
        
        if(location.y>theAppProfile.theHeight){
            velocity.y *= -1;
            location.y = theAppProfile.theHeight;
            pApp.println("BOTTOM HIT");
            doBounceSound();
            theAppProfile.scoredata += 23;
            


        } else if (location.y<0){
            location.y = 0;  //// make sure the location doesn't go above the screen
            velocity.y *=-.25;
            doBounceSound();
            theAppProfile.scoredata += 23;

        }
        
    }
    
    void doImpactColor(){
    	pApp.println("IMPACT COLOR");
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
    
    void doBounceSound(){
       int theRnd = (int)pApp.random(4);
   	   theSoundControl.playBasketballSound(theRnd);
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
       ///// pApp.rotateX(pApp.map(location.x, 0,600, 1.9f, -1.9f));
        if(hasImpact){
        	theSpin +=2.1;
            pApp.rotateZ(theSpin);
        } else {
        	theSpin = .001f;
        }
        
        pApp.sphere(50);
        // pApp.sphere(50);
        pApp.popMatrix();

    }
    
}
/// end mover class
