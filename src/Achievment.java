import processing.core.*;

class Achievment {
	
	String cheevName;
	String cheevDesc;
	String cheevPath;
	
	float tX;
	float tY;
	
	int theMargin = 20;
	
	PApplet pApp;
	AppProfile theAppProfile;
	
	PFont DescFont;
	PFont HeaderFont;
	 
	PImage cheevImage;
	
	Achievment(String cn, String cd, String cp){
		
		 theAppProfile = theAppProfile.getInstance();
		 pApp = theAppProfile.pApp;
		 
			
	     cheevName = cn;
	     cheevPath = cp;
	     cheevDesc = cd;
		 cheevImage = pApp.loadImage(cp);
		 
		 HeaderFont = pApp.createFont("Neutra Text",22, true); /// normal fonts
		 DescFont = pApp.createFont("Neutra Text",18, true); // use true/false for smooth/no-smooth for Control fonts
			 
	}
	
	public void displayCheevo(){
		
		pApp.textFont(HeaderFont, 22);
		pApp.text(cheevName, tX + theMargin*2, tY);
		pApp.textFont(DescFont, 22);
		pApp.text(cheevDesc, tX+theMargin*2, tY + 20);
		
		pApp.image(cheevImage, tX, tY);
	}

} // end achievment
