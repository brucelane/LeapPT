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
		 cheevImage.resize(30, 30);
		 
		 HeaderFont = pApp.createFont("Neutra Text",22, true); /// normal fonts
		 DescFont = pApp.createFont("Neutra Text",18, true); // use true/false for smooth/no-smooth for Control fonts
			 
	}
	
	public void displayCheevo(){
		
		pApp.textFont(HeaderFont, 20);
		pApp.text(cheevName, tX + theMargin, tY);
		pApp.textFont(DescFont, 16);
		pApp.text(cheevDesc, tX+theMargin, tY + 20);
		
		pApp.image(cheevImage, tX - cheevImage.width/2, tY - cheevImage.width/2);
	}

} // end achievment
