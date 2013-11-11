import processing.core.*;


public class BreakoutBox {
	PApplet pApp;
	AppProfile theAppProfile;
	
	int numHits; // how many hits the box can get before it breaks
	
	int boxColor;
	
	/// positioning
	float theX;
	float theY;
	float theZ;
	float theWidth;
	float theHeight;
	
    /// colors 
    int theColor;
    int theColor2;
    int hitColor;
    int theInsideClr;
    
    int theR = 255;
    int theG = 164;
    int theB = 0;
    int theA = 255;
    
    //
    boolean isLive = true;
    boolean isHit = false;

	//// x position, y position, z position, width, height, color
	BreakoutBox(float tX, float tY, float tZ, float tWidth, float tHeight, int tColor){
		theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;

		theX = tX;
		theY = tY;
		theZ = tZ;
		
		theWidth = tWidth;
		theHeight = tHeight;
		
		boxColor = pApp.color(tColor, theA);
		hitColor = pApp.color(0,255,0);
		theColor2 = pApp.color(0,0,255);
	}
	
	
	public void drawBox(){
		//// 
	   if(isHit == true){
		   theA -=10;
		   if(theA < 0 && numHits >0){
			  isHit = false; 
			  hitColor = theColor2;
			  theA = 185;
		   }
		   if(theA < 20 && numHits <=0){
		      isLive = false; 
		   }
		   
		   
	       boxColor = pApp.color(hitColor, theA);
       }

		pApp.fill(boxColor);
		pApp.rect(theX, theY, theWidth, theHeight);

		pApp.fill(boxColor, theA + 20);
		pApp.rect(theX + 5, theY + 5, theWidth - 10, theHeight - 10);
		
		//// 
	}
	
	
	public void breakBox(){
		
		
	}
	
	//// CHANGES THE COLOR FOR THE BOX
	public void doHit(){
		numHits --;
		isHit = true;
		theR = 0;
		theG = 255;
		theB = 255;
	}
	
	
}
