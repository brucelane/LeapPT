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

	//// x position, y position, z position, width, height, color
	BreakoutBox(float tX, float tY, float tZ, float tWidth, float tHeight, int tColor){
		theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;

		theX = tX;
		theY = tY;
		theZ = tZ;
		
		theWidth = tWidth;
		theHeight = tHeight;
		
		boxColor = tColor;
	}
	
	
	public void drawBox(){
		
		pApp.fill(boxColor);
		pApp.rect(theX, theY, theWidth, theHeight);
		//// 
	}
	
	
	public void breakBox(){
		
		
	}
	
	
}
