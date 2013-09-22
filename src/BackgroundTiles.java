import java.util.ArrayList;

import processing.core.*;


	
public class BackgroundTiles extends PApplet{
	
	PVector location;
    PVector velocity;
    PVector acceleration;
    PVector wind;
    PVector gravity;
    PVector friction;
	
	PApplet pApp;
	AppProfile theAppProfile;
	
	/// image data
	PImage leftImg;
	PImage centerImg;
	PImage rightImg;
	
	///
	PImage img0;
	PImage img1;
	PImage img2;
	PImage img3;
	PImage img4;
	PImage img5;
	PImage img6;
	PImage img7;
	PImage img8;
	PImage img9;
	
	
	
	ArrayList<String> bgroundImages = new ArrayList();
	ArrayList<PImage> bgroundImgHolder = new ArrayList();
	
	private static BackgroundTiles instance = null;

	int numRows = 20;
	int numCols = 20;
	
	int squareWidth = 64;
	int squareHeight = 64;
	
    float baseRotX = .01f;
    float baseRotY = .01f;
    float baseRotZ = .01f;
    
    int curID = 0;
	
	int color1;
	int color2;
	
	float originX = 0;
	float originY = 0;
	

    float mass;
    
    
	
    BackgroundTiles(float m, float x, float y) {
		/// set up app profile
		theAppProfile = theAppProfile.getInstance();
		pApp = theAppProfile.pApp;
		
		color1 = pApp.color(165);
		color2 = pApp.color(0);
		
        mass = m;
        location = new PVector(0,0);
        /// location = new PVector(pApp.random(-theAppProfile.theWidth/2), pApp.random(-theAppProfile.theHeight/2));
        //// velocity = new PVector(pApp.random(-2.0f,2.0f),pApp.random(-2.0f,2.0f));
        /// no initial velocity
        velocity = new PVector(0.0f, 0.0f);
        acceleration = new PVector(0.0f,0.01f);
        friction = new PVector(-.01f, -.01f, -.01f);
        
        
        /// add images
        bgroundImages.add("data/images/bground_template_01.png");
        bgroundImages.add("data/images/bground_template_02.png");
        bgroundImages.add("data/images/bground_template_03.png");
        bgroundImages.add("data/images/bground_template_04.png");
        bgroundImages.add("data/images/bground_template_05.png");
        bgroundImages.add("data/images/bground_template_06.png");
        bgroundImages.add("data/images/bground_template_07.png");
        bgroundImages.add("data/images/bground_template_08.png");
        bgroundImages.add("data/images/bground_template_09.png");
        /// add images
        
        /*
        leftImg = pApp.loadImage(bgroundImages.get(curID -1));
        centerImg = pApp.loadImage(bgroundImages.get(curID));
        rightImg = pApp.loadImage(bgroundImages.get(curID +1));
        */
        img0 = pApp.loadImage(bgroundImages.get(curID));
        img1 = pApp.loadImage(bgroundImages.get(curID + 1));
        img2 = pApp.loadImage(bgroundImages.get(curID + 2));
        img3 = pApp.loadImage(bgroundImages.get(curID + 3));
        img4 = pApp.loadImage(bgroundImages.get(curID + 4));
        img5 = pApp.loadImage(bgroundImages.get(curID + 5));
        img6 = pApp.loadImage(bgroundImages.get(curID + 6));
        img7 = pApp.loadImage(bgroundImages.get(curID + 7));
        img8 = pApp.loadImage(bgroundImages.get(curID + 8));

        
        ///*
        bgroundImgHolder.add(img0);
        bgroundImgHolder.add(img1);
        bgroundImgHolder.add(img2);      
        bgroundImgHolder.add(img3);
        bgroundImgHolder.add(img4);   
        bgroundImgHolder.add(img5);   
        bgroundImgHolder.add(img6);     
        bgroundImgHolder.add(img7);    
        bgroundImgHolder.add(img8);  
 
        
        //*/
        
        
        
        //// img8 = pApp.loadImage(bgroundImages.get(curID));
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
		// normalize all movement

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
		
		int mul = 1;
    	
		/*
    	if(acceleration.x < -.01){
    		mul = 1;
    		theAppProfile.scoredata += acceleration.x*mul;
        	// theAppProfile.scoredata += acceleration.z*12*mul;
        	// theAppProfile.scoredata += acceleration.y*12*mul;
    	} else if(acceleration.x > .01) {
    		mul = -1;
    		theAppProfile.scoredata += acceleration.x*mul;
        	// theAppProfile.scoredata += acceleration.z*12*mul;
        	/// theAppProfile.scoredata += acceleration.y*12*mul;
    		
    	}
    	
    	*/
    	if(velocity.x < -.01){
    		mul = -1;
    		theAppProfile.scoredata += velocity.x*mul;
        	// theAppProfile.scoredata += acceleration.z*12*mul;
        	// theAppProfile.scoredata += acceleration.y*12*mul;
    	} else if(velocity.x > .01) {
    		mul = 1;
    		theAppProfile.scoredata += velocity.x*mul;
        	// theAppProfile.scoredata += acceleration.z*12*mul;
        	/// theAppProfile.scoredata += acceleration.y*12*mul;
    		
    	}

    	
    	//  pApp.println(theAppProfile.scoredata + " accel " + acceleration.x + " vel x " + velocity.x);
    	 /// pApp.println(theAppProfile.scoredata + " accel " + acceleration.y + " vel y " + velocity.y);
    	
    	///  pApp.println("X: " + acceleration.x + " Y: " + acceleration.y + " Z : " + acceleration.z);	
		
		// pApp.println("Accell z " + acceleration.z);
		
		velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
	
	}
	
	public void display(){
		originX = location.x;
		originY = location.y;
		
		
		if(location.x + (theAppProfile.theWidth * bgroundImages.size()) <= theAppProfile.theWidth){
			location.x = 0;
		}
		
		if(location.x >0){
			
			location.x = 0;
		}
		/// if the right edge of the background is less than two tiles more than the right edge
		/*
		if(location.x + theAppProfile.theWidth <= theAppProfile.theWidth){
			
			/// increment counter
			curID ++;
			
			/// reset to two tiles from the left side
			location.x = 0;
			
			/// reposition images

			/// change it to the next image in the array
			
			/// add it to the rightmost position
		}
		
		
		/// if the left edge of the background is greater than two tiles from the left side
		if(location.x >= -(squareWidth*2)){
			
			/// reset to two tiles from the left side
			location.x = (-squareWidth * 2);
			
			
			curID --;
			
			

			/// remove the leftmost tile
			
			/// change it to the previous entry in the array
			
			/// add it to the leftmost position
		}
		
		*/
		
		/*
		pApp.image(leftImg, location.x, 0);
        pApp.image(centerImg, location.x + theAppProfile.theWidth, 0);
        pApp.image(rightImg, location.x + theAppProfile.theWidth *2, 0);
        */
		
		/*
		pApp.image(img0, location.x + theAppProfile.theWidth *0, 0);
        pApp.image(img1, location.x + theAppProfile.theWidth *1, 0);
        pApp.image(img2, location.x + theAppProfile.theWidth *2, 0);
        pApp.image(img3, location.x + theAppProfile.theWidth *3, 0);
        pApp.image(img4, location.x + theAppProfile.theWidth *4, 0);
        pApp.image(img5, location.x + theAppProfile.theWidth *5, 0);
        pApp.image(img6, location.x + theAppProfile.theWidth *6, 0);
        pApp.image(img7, location.x + theAppProfile.theWidth *7, 0);
        */
       // /*
        
		for(int i=0; i<bgroundImgHolder.size(); i++){
			
			try{
				/// pApp.println(i);
				PImage tImg = (PImage) bgroundImgHolder.get(i);
				pApp.image(tImg, location.x + (theAppProfile.theWidth *i), 0);
				// pApp.image(tImg, location.x + theAppProfile.theWidth + (theAppProfile.theWidth *i), 0);
			} catch (Exception e){
				pApp.println("Can't display image: " + i +  " " + bgroundImgHolder.get(i));
			}
		}
		
		/// */
		
		
		
	}
	
	/////
	
	
}
