import processing.core.*;

import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;


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
    
    int theColor;
    int theR = 255;
    int theG = 165;
    int theB = 0;
    int theA = 165;
    
    Poly paddleHitState;
    
    boolean isHit = false;
    
    int paddleY;
    
    float friction = .01f; /// this is the slowdown for the movement
    
    // listeners
    protected EventListenerList listenerList = new EventListenerList();
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

       /// check if is hit
        if(location.x + paddleWidth/2 > m.location.x -30 && location.x - paddleWidth/2 < m.location.x + 30 && paddleY > m.location.y -30 && paddleY < m.location.y + 30){
        	/// if so, find the side it's hitting and
        	/// bounce it in the other direction
        	
        	
        	// to do--redo bounces
        	if(location.x > m.location.x -30){
        		m.velocity.x *= -1; 
        	}
        	/// move it in the other direction
        	if(location.x < m.location.x + 30){
        		m.velocity.x *= 1; 
        	}
        	
        	
        	/// if it's hitting the bottom or top
        	/// then move it laterally depending on
        	/// what side it's hit on
        	if(paddleY > m.location.y -30){
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
        	if(paddleY < m.location.y + 30){
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
        	paddleImpactColor();
        	m.doImpactColor();
        	m.doPaddleHit();
        	
        	/// do cheevo
        	fireMyEvent(null, "First Bounce");
        	//
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
      
        distance = pApp.constrain(distance, 5.0f, 25.0f);
        
        float newG;
       
        force.normalize();
        float strength = (G * mass * m.mass) / (distance * distance);
        force.mult(strength);
        return force;
    } 
    
    void paddleImpactColor(){
    	pApp.println("IMPACT COLOR");
        theR = 0;
        theG = 255;
        theB = 0;
        theA = 255;
    	
    }
    //
    void display(){
    	
    	/// normalize paddle color
    	 if(theR < 255){
         	theR +=10;

         }
         if(theG > 165){
         	theG -=10;

         }
         if(theB > 0){
         	theB -=10;

         }
         if(theA > 165){
         	theA -=10;

         } 
         
         theColor = pApp.color(theR,theG,theB,theA);
         
    	// pApp.noFill();
    	pApp.fill(theColor);
    	pApp.strokeWeight(0);
    	/// pApp.stroke(255,200);
    	pApp.rect((location.x-paddleWidth/2), paddleY, paddleWidth - 28, paddleHeight);

    	/// add paddle image
    	pApp.image(paddleImg, (location.x-paddleWidth/2) -14, paddleY);
    	//// this updates the particles
    	ps.run();
    }
    
    ////// listeners
    public void addMyEventListener(MyEventListener listener) {
        listenerList.add(MyEventListener.class, listener);
      }
      public void removeMyEventListener(MyEventListener listener) {
        listenerList.remove(MyEventListener.class, listener);
      }
      void fireMyEvent(MyEvent evt, String tCheev) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
          if (listeners[i] == MyEventListener.class) {
            ((MyEventListener) listeners[i+1]).myEventOccurred(evt, tCheev);
          }
        }
      }


    
}//// end paddle class
