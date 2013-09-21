import processing.core.*;


	
public class Checkerboard extends PApplet{
	
	PVector location;
    PVector velocity;
    PVector acceleration;
    PVector wind;
    PVector gravity;
    PVector friction;
	
	PApplet pApp;
	AppProfile theAppProfile;
	
	
	private static Checkerboard instance = null;

	int numRows = 17;
	int numCols = 17;
	
	int squareWidth = 200;
	int squareHeight = 200;
	
    float baseRotX = .01f;
    float baseRotY = .01f;
    float baseRotZ = .01f;
	
	int color1;
	int color2;
	
	float originX = 0;
	float originY = 0;
	

    float mass;
	
	Checkerboard(float m, float x, float y) {
		/// set up app profile
		theAppProfile = theAppProfile.getInstance();
		pApp = theAppProfile.pApp;
		
		color1 = pApp.color(165);
		color2 = pApp.color(0);
		
        mass = m;
        location = new PVector(pApp.random(-theAppProfile.theWidth/2), pApp.random(-theAppProfile.theHeight/2));
        //// velocity = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
        /// no initial velocity
        velocity = new PVector(0.0f, 0.0f);
        acceleration = new PVector(0.0f,0.01f);
        friction = new PVector(-.01f, -.01f, -.01f);
	    
	}
	
	void initCheckerboard(){
	
		
	}
	
	void doImpact(float tX, float tY, float tZ){
    	acceleration.x = tX;
    	acceleration.y = tY;
    	acceleration.z = tZ;
    	
    	acceleration.x = tX;
    	acceleration.y = tY;
    	acceleration.z = tZ;
	}
	
	 /// force
    void applyForce(PVector force){
        PVector f = PVector.div(force,mass);
        acceleration.add(f);
        
    }
    
    ///// check edges
    void checkEdges(){
    	/*
        if(location.x>theAppProfile.theWidth){
            location.x = theAppProfile.theWidth;
            velocity.x *= -1;
           

        } else if (location.x<0){
            velocity.x *= -1;
            location.x = 0;
            
            
        }
        
        if(location.y>theAppProfile.theHeight){
            velocity.y *= -1;
            location.y = theAppProfile.theHeight;
            

        } else if (location.y<0){
            location.y = 0;
            velocity.y *=-1;


        }
        */
        
    }
    

	public void update(){
 // normalize movement


		if(velocity.x > 0.02){
			velocity.x += friction.x;
			// // pApp.println(" velocity.x > 0.02 : " + velocity.x + " + " + friction.x);
		}
		
		if( velocity.x < -0.02){
			velocity.x -= friction.x;
			// pApp.println(" velocity.x < -0.02 : " + velocity.x + " + " + friction.x);
		}
		
		if(velocity.x < 0.02 && velocity.x > -.002){
			velocity.x = 0;
			velocity.x = 0;
			/// // pApp.println("Zero!");
		}
		
		/////
		
		if(velocity.y > 0.02){
			velocity.y += friction.y;
			// pApp.println("velocity.y > 0.02" + velocity.y + " + " + friction.y);
		}
		
		if( velocity.y < -0.02){
			velocity.y -= friction.y;
			// pApp.println("velocity.y < -0.02 " + velocity.y + " + " + friction.y);
		}
		
		if(velocity.y < 0.02 && velocity.y > -.02){
			velocity.y = 0;
			velocity.y = 0;
		}
		
		if(velocity.z > 0.02){
			velocity.z += friction.z;
			// pApp.println("velocity.z > 0.02" + velocity.z + " + " + friction.z);
		}
		
		if( velocity.z < -0.02){
			velocity.z -= friction.z;
			// pApp.println("velocity.z < -0.02 " + velocity.z + " + " + friction.z);
		}
		
		if(velocity.z < 0.02 && velocity.z > -.02){
			velocity.z = 0;
			velocity.z = 0;
		}
		
	
		
		// pApp.println("Accell z " + acceleration.z);
		
		velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
	
	}
	
	public void display(){
		originX = location.x;
		originY = location.y;
		
		
		if(location.x + (numCols * squareWidth) <= theAppProfile.theWidth + (squareWidth *2)){
			location.x = (-squareWidth * 2);
		}
		
		if(location.x >= -(squareWidth*2)){
			location.x = (-squareWidth * 2);
		}
		
		////
		
		if(location.y + (numRows * squareHeight) <= theAppProfile.theHeight + (squareHeight *2)){
			location.y = (-squareHeight * 2);
		}
		
		if(location.y >= -(squareHeight*2)){
			location.y = (-squareHeight * 2);
		}
		
		
		pApp.pushMatrix();
		pApp.translate(0,0, -200);
		for (int i=0; i<numRows; i++){
			
			for (int j=0; j<numCols; j++){
				
				
				if (i % 2 == 0) {
			        if (j % 2 == 0) {
			          pApp.fill(color1);
			        } else {
			        	pApp.fill(color2);
			        }
			      } else {
			        if (j % 2 != 0) {
			        	pApp.fill(color1);
			        } else {
			        	pApp.fill(color2);
			        }
			     }
				pApp.noStroke();
				pApp.rect(i*squareWidth + originX, j*squareHeight + originY, squareWidth, squareHeight);
				
			}
			
		}
		pApp.popMatrix();
		
		
		
	}
	
	/////
	
	
}
