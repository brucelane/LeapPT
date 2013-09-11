// A simple Particle class
import processing.core.*;

class Particle {
	
  PApplet pApp;
  AppProfile theAppProfile;
  PVector location;
  PVector velocity;
  PVector acceleration;
  float lifespan;
  
  int innerColor;
  int outerColor;

  Particle(PVector l) {
	theAppProfile =  theAppProfile.getInstance();
	pApp = theAppProfile.pApp;
    acceleration = new PVector(0,0.09f);
    velocity = new PVector(pApp.random(-1,1),pApp.random(-2,0));
    location = l.get();
    lifespan = 255.0f;
    
    innerColor = pApp.color(255);
    outerColor = pApp.color(255,255,0);
  }

  void run() {
    update();
    display();
  }

  // Method to update location
  void update() {
    velocity.add(acceleration);
    location.add(velocity);
    lifespan -= 1.0;
  }

  // Method to display
  void display() {
	pApp.stroke(outerColor,lifespan-20);
	pApp.fill(innerColor,lifespan);
	pApp.ellipse(location.x,location.y,4,4);
  }
  
  // Is the particle still useful?
  boolean isDead() {
    if (lifespan < 0.0) {
      return true;
    } else {
      return false;
    }
  }
}