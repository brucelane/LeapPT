import processing.core.*;


import processing.opengl.PGraphicsOpenGL;


class Feather{
	
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
    
    
    PImage theTexture;
    String texturePath = "data/feather_texture.png";
    
    PImage bgroundImg;
    String theBgroundImgPath = "data/backgrounds/featherweight_bg.png";
    int tSize;
    
    /// the mass, the x, the y);
    Feather(float m){
    	mass = m;
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;

    	theSoundControl = theSoundControl.getInstance();
    	 // = new SoundControl();
    	
    	theColor = pApp.color(theR,theG,theB,theA);

        theTexture = pApp.loadImage(texturePath);
        bgroundImg = pApp.loadImage(theBgroundImgPath);
        
        gravity = new PVector(0, 0.0125f);
        initMovement();
        
    }
    
    void initMovement(){
        
        location = new PVector(theAppProfile.theWidth/2, pApp.random(theAppProfile.theHeight/4));
        velocity = new PVector(0,pApp.random(0,1.50f));
        acceleration = new PVector(0.0f,0.2f);
        ////
    	
    }
    /// force
    void applyForce(PVector force){
    	
    	/// switching from div to mult seems to do nothing
        PVector f = PVector.div(force,mass);
        /// acceleration.add(f);
        
    }
    
    void update(){
    	
    	if(gravity.y <= 0.025f){
    		
    		gravity.y += .01;
    		
    		/// pApp.println("Grav: " + gravity.y);
    	}
        velocity.add(acceleration);
        velocity.add(gravity);
        location.add(velocity);
        
        /// acceleration.limit(1);
        // velocity.limit(2);
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
            // velocity.y *= -1;
            // location.y = theAppProfile.theHeight;
            location.y = 0;
           
            doBounceSound();
            // initMovement();
            


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
    	pApp.image(bgroundImg,0,0);
	  	 // pApp.rect(0,0,theAppProfile.theWidth, theAppProfile.theHeight);
 	  
   	/*
       pApp.stroke(255,255,255, 125);
       pApp.strokeWeight(1);
       pApp.fill(255,255,255, 0);

		/// pApp.textureMode(pApp.NORMAL);
   	/*
       pApp.fill(255,0,0);
       pApp.rect(0,0, theAppProfile.theWidth, theAppProfile.theHeight);
       */
       pApp.pushMatrix();
       pApp.translate(location.x, location.y, 100); /// x,y and z?
       
     /// add the x and y position to the movement
       // pApp.rotateY(pApp.map(location.y, 0,600, 0.0f, 0.5f));
       pApp.rotateZ(-35);
       pApp.rotateX(5.5f);
       
       // pApp.rotateX(pApp.map(location.x, 0,600, 1.9f, -1.9f));
       
       // pApp.scale(1, .5f);
       //pApp.scale(90);
       
       // pApp.box(300);

       pApp.beginShape();
   	   pApp.fill(0,0,255,255);
	   pApp.noStroke();
	  	
       pApp.texture(theTexture);
       pApp.vertex(-100, -100, 0, 0, 0);
       pApp.vertex(100, -100, 0, theTexture.width, 0);
       pApp.vertex(100, 100, 0, theTexture.width, theTexture.height);
       pApp.vertex(-100, 100, 0, 0, theTexture.height);
       pApp.endShape();
       

       ///// DOES TEXTURE
       /*
       pApp.beginShape(pApp.QUADS);
       pApp.texture(theTexture);

       // Given one texture and six faces, we can easily set up the uv coordinates
       // such that four of the faces tile "perfectly" along either u or v, but the other
       // two faces cannot be so aligned.  This code tiles "along" u, "around" the X/Z faces
       // and fudges the Y faces - the Y faces are arbitrarily aligned such that a
       // rotation along the X axis will put the "top" of either texture at the "top"
       // of the screen, but is not otherwised aligned with the X/Z faces. (This
       // just affects what type of symmetry is required if you need seamless
       // tiling all the way around the cube)
       
       // +Z "front" face
       pApp.vertex(-1, -1,  1, 0, 0);
       pApp.vertex( 1, -1,  1, 1, 0);
       pApp.vertex( 1,  1,  1, 1, 1);
       pApp.vertex(-1,  1,  1, 0, 1);

       // -Z "back" face
       pApp.vertex( 1, -1, -1, 0, 0);
       pApp.vertex(-1, -1, -1, 1, 0);
       pApp.vertex(-1,  1, -1, 1, 1);
       pApp.vertex( 1,  1, -1, 0, 1);

       // +Y "bottom" face
       pApp.vertex(-1,  1,  1, 0, 0);
       pApp.vertex( 1,  1,  1, 1, 0);
       pApp.vertex( 1,  1, -1, 1, 1);
       pApp.vertex(-1,  1, -1, 0, 1);

       // -Y "top" face
       pApp.vertex(-1, -1, -1, 0, 0);
       pApp.vertex( 1, -1, -1, 1, 0);
       pApp.vertex( 1, -1,  1, 1, 1);
       pApp.vertex(-1, -1,  1, 0, 1);

       // +X "right" face
       pApp.vertex( 1, -1,  1, 0, 0);
       pApp.vertex( 1, -1, -1, 1, 0);
       pApp.vertex( 1,  1, -1, 1, 1);
       pApp.vertex( 1,  1,  1, 0, 1);

       // -X "left" face
       pApp.vertex(-1, -1, -1, 0, 0);
       pApp.vertex(-1, -1,  1, 1, 0);
       pApp.vertex(-1,  1,  1, 1, 1);
       pApp.vertex(-1,  1, -1, 0, 1);

       pApp.endShape();
       
       */
       
       pApp.popMatrix();

    }
    
}
/// end mover class
