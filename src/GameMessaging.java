import processing.core.*;
import controlP5.*; /// may have to remove this

public class GameMessaging {
	PApplet pApp;
	AppProfile theAppProfile;
	
	float xPos;
	float yPos;
	int theMargin = 20;
	
	float theTime;
	
	String headerText;
	String bodyText;
	
	PFont headerFont;
	PFont bodyFont;
	
	int headerColor;
	int bodyColor;
	
	PImage theBground;
	PImage xtraImage;
	PImage closeImg;
	PImage closeImgR;
	
	// general ui
    Button closeButton;
	
	boolean showingMessage = false;
	
	TimerClass theTimer;
	
	ControlP5 cp5;
	ControlP5 ControlEvent;
	
	///// the x, the y, the header text, the body text, the header color, the body color
	GameMessaging(){
		
		theTimer = new TimerClass();
		theAppProfile = theAppProfile.getInstance();
		pApp = theAppProfile.pApp;
		
		theBground = pApp.loadImage("data/game_message_bground.png");
		closeImg = pApp.loadImage("data/close_button.png");
		closeImgR = pApp.loadImage("data/close_buttonR.png");
		
		cp5 = new ControlP5(pApp);
		
		PImage[] closeMenuImg = {closeImg,closeImgR,closeImgR};
		closeButton = cp5.addButton("CLOSEGAMEMESS")  /// close game message
				  .setPosition(theAppProfile.theWidth/2 -theBground.width/2 + theBground.width- closeImg.width/2, theAppProfile.theHeight/2 - theBground.height/2 - closeImg.height/2)
				  .updateSize()
				  .setImages(closeMenuImg)
				  .updateSize()
			     
				  ;
				 
				 
				 closeButton.hide(); 
		
	}
	
	public void initMessage(float x, float y, String header, String body, int hC, int bC){
		xPos = x;
		yPos = y;
		headerText = header;
		bodyText = body;

		headerColor = hC;
	    bodyColor = bC;
		
	    headerFont = pApp.createFont("Gotham Rounded",22, true); /// normal fonts
		bodyFont = pApp.createFont("Gotham Rounded",18, true); //
		
		/// only do this if we want a timer to auto-close
		// theTimer.start();
		showingMessage = true;
		
		
		xPos = theAppProfile.theWidth/2 -theBground.width/2;
		yPos = theAppProfile.theHeight/2 - theBground.height/2;
		
		closeButton.show(); 
		
	}

	
	public void drawMessage(){
		
		/// draw background
		pApp.image(theBground, theAppProfile.theWidth/2 -theBground.width/2, theAppProfile.theHeight/2 - theBground.height/2);
		/// draw text
		pApp.textFont(headerFont, 18);
		pApp.fill(headerColor);
		pApp.text(headerText,  xPos + theMargin, yPos + theMargin, theBground.width - theMargin, theAppProfile.theHeight);
		
		pApp.fill(bodyColor);
		pApp.textFont(bodyFont, 16);
		pApp.text(bodyText,  xPos + theMargin, yPos + theMargin * 3, theBground.width - theMargin*2f, theAppProfile.theHeight);
		
		/*
		if(theTimer.getElapsedTime() >= 5000 ){

			theTimer.stop();
			
			showingMessage = false;
			
			closeButton.hide(); 
		 }
		 */
	}
	
	public void closeMessage(){
		theTimer.stop();
		showingMessage = false;
		closeButton.hide(); 
		
	}
	
	
} /// end game messaging
