import processing.core.*;
import processing.opengl.PGraphicsOpenGL;

import java.util.EventListener;
import java.util.EventObject;
import javax.swing.event.EventListenerList;

class Spinner{
	
	PApplet pApp;
	AppProfile theAppProfile;
	PlayerProfile thePlayerProfile;
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
    
    float baseRotX = .00f;
    float baseRotY = .01f;
    float baseRotZ = .01f;
    
    
    float curRotX = .5f;
    float curRotY = .5f;
    float curRotZ = .5f;
    
    boolean isSpinningX = false;
    boolean isSpinningY = false;
    boolean isSpinningZ = false;
    
    float theSpin = 0.01f;
    float baseSpin = 0.01f;

    float mass;
    int ballSize = 35;
    
    boolean hasImpact = false;
    
    PImage feetImg;
    PImage shadowImg;
    String feetImgPath = "data/games/feet_circle_medium.png";
    String shadowPath = "data/games/feet_shadow.png";
    
    // listeners
    protected EventListenerList listenerList = new EventListenerList();
    /// the mass, the x, the y);
    Spinner(float m, float x, float y){
    	
    	theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;

    	theSoundControl = theSoundControl.getInstance();
    	
    	thePlayerProfile = thePlayerProfile.getInstance();
    	 // = new SoundControl();
    	
    	theColor = pApp.color(theR,theG,theB,theA);
    	
        mass = m;
        /// this always starts in the middle of the screen
        location = new PVector(theAppProfile.theWidth/2, theAppProfile.theHeight/2);
        velocity = new PVector(0.0f, 0.0f);
        acceleration = new PVector(0.0f,0.01f);
        friction = new PVector(-.01f, -.01f, -.01f);
        
        feetImg = pApp.loadImage(feetImgPath);
        shadowImg = pApp.loadImage(shadowPath);
        
    }
    
    /// force
    void applyForce(PVector force){
        PVector f = PVector.div(force,mass);
        acceleration.add(f);
        
    }

    public void update(){
		// normalize all movement

		if(velocity.x > 0.02){
			velocity.x += friction.x;
			isSpinningX = true;
			// pApp.println(" velocity.x > 0.02 : " + velocity.x + " + " + friction.x);
		}
		
		if( velocity.x < -0.02){
			velocity.x -= friction.x;
			isSpinningX = true;
			// pApp.println(" velocity.x < -0.02 : " + velocity.x + " + " + friction.x);
		}
		
		if(velocity.x < 0.02 && velocity.x > -.02){
			velocity.x = 0;
			velocity.x = 0;
			
			isSpinningZ = false;
			// pApp.println("Zero! X");
		}
		
		/////
		
		if(velocity.y > 0.02){
			velocity.y += friction.y;
			isSpinningY = true;
			// pApp.println("velocity.y > 0.02" + velocity.y + " + " + friction.y);
		}
		
		if( velocity.y < -0.02){
			velocity.y -= friction.y;
			isSpinningY = true;
			// pApp.println("velocity.y < -0.02 " + velocity.y + " + " + friction.y);
		}
		
		if(velocity.y < 0.02 && velocity.y > -.02){
			velocity.y = 0;
			velocity.y = 0;
			// pApp.println("Zero! Y");
			
			isSpinningY = false;
		}
		
		if(velocity.z > 0.02){
			velocity.z += friction.z;
			isSpinningZ = true;
			// pApp.println("velocity.z > 0.02" + velocity.z + " + " + friction.z);
		}
		
		if( velocity.z < -0.02){
			velocity.z -= friction.z;
			isSpinningZ = true;
			// pApp.println("velocity.z < -0.02 " + velocity.z + " + " + friction.z);
		}
		
		if(velocity.z < 0.02 && velocity.z > -.02){
			velocity.z = 0;
			velocity.z = 0;
			
			isSpinningZ = false;
			// pApp.println("Zero! Z");
		}

		/// if all three velocities are not zero, keep adding to the score
		if(velocity.x != 0 || velocity.y != 0 || velocity.z != 0){
			
			thePlayerProfile.GameStats.get(theAppProfile.gameID).curScore -= velocity.x;
			float curDistance = thePlayerProfile.GameStats.get(theAppProfile.gameID).curScore;
			
			// check for achievments
			/// if more than five feet
			if(curDistance > 5 && curDistance < 15){
				
				
	        	launchCheevo(null, "Small Steps");
			}
			
			if(curDistance > 20 && curDistance < 25){
				
				
	        	launchCheevo(null, "Toddler");
			}
			
			if(curDistance > 1350 && curDistance <1355){
				
				launchCheevo(null, "Quarter Horse");
				
			}
			
			
			
			
			if(curDistance > 5280 && curDistance < 5285){
	        	launchCheevo(null, "Milestone");
			}
			
		}
		
		/// check to see if has stopped
		
		if(isSpinningX == false && isSpinningY == false && isSpinningZ == false){
			
			//// pApp.println("SPINNER HAS STOPPED!");
		}
		
		
		velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
	
	}
    
    
   
    
    ////// this is not used?
    public void doImpact(float tX, float tY, float tZ){
    	hasImpact = true;
    	doImpactColor();
    	doSpinSounds();
    	
    	acceleration.x = tX;
    	acceleration.y = tY;
    	acceleration.z = tZ;
    	
    	acceleration.x = tX;
    	acceleration.y = tY;
    	acceleration.z = tZ;
    	
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
	   theSoundControl.playLaserSounds(theRnd);

    }
    
    void doSpinSounds(){
       int theRnd = (int)pApp.random(4);
   	   theSoundControl.playStarWarsSound(theRnd);
   	   /// pApp.println(theRnd);
    }
    
    
    void display(){
    	
        pApp.stroke(255,0,0);
        pApp.strokeWeight(1);
        pApp.fill(theColor);
        
        pApp.image(shadowImg, theAppProfile.theWidth/2- shadowImg.width/2, theAppProfile.theHeight - shadowImg.height + 30);
        pApp.pushMatrix();
        pApp.translate(theAppProfile.theWidth/2 , theAppProfile.theHeight/2 + 100); /// x,y and z?
        
        
        pApp.rotate((location.x *-1) * 0.020f);
        /// pApp.rotate(curRotX += velocity.x);
        
        pApp.translate(-feetImg.width/2, -feetImg.height/2); /// x,y and z?
       //  pApp.translate(location.x, location.y); /// x,y and z?
        
        /*
        pApp.rotateY(curRotX += velocity.x);
        pApp.rotateX(curRotY += velocity.y);
    	pApp.rotateZ(curRotZ += velocity.z);
        
        pApp.box(200);
        
        */
        // pApp.image(feetImg, location.x, location.y);
        pApp.image(feetImg, 0, 0);
        
        
        /// feet_circle_250.png
        // pApp.sphere(50);
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
    	  pApp.println("EVENT: " + tCheev);
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == MyEventListener.class) {
            ((MyEventListener) listeners[i+1]).myEventOccurred(evt, tCheev);
          }
        }
      }
      
    
}/// end mover class
