import processing.core.*;
import controlP5.*; /// may have to remove this

public class GameMessaging {
	
	private static GameMessaging instance = null;
	 
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
	PImage theBgroundLg;
	PImage xtraImage;
	PImage closeImg;
	PImage closeImgR;
	
	// general ui
    Button closeButton;
	
	boolean showingMessage = false;
	boolean isLargeMessage = false;
	
	TimerClass theTimer;
	
	ControlP5 cp5;
	ControlP5 ControlEvent;
	
	///// the x, the y, the header text, the body text, the header color, the body color
	protected GameMessaging(){
		
		theTimer = new TimerClass();
		theAppProfile = theAppProfile.getInstance();
		pApp = theAppProfile.pApp;
		
		theBground = pApp.loadImage("data/game_message_bground.png");
		theBgroundLg = pApp.loadImage("data/game_message_bground_lg.png");
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
	
	////// defeat instantiation
	public static GameMessaging getInstance() {
	      if(instance == null) {
	         instance = new GameMessaging();
	      }
	      return instance;
	   }
	
	public void initMessage(float x, float y, String header, String body, String imgPath, int hC, int bC){
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
		
		// pApp.println(imgPath);
		if(imgPath == null){
			isLargeMessage = false;
			
			
		} else {
		
			isLargeMessage = true;
			xtraImage = pApp.loadImage(imgPath);
		}
		xPos = theAppProfile.theWidth/2 -theBground.width/2;
		yPos = theAppProfile.theHeight/2 - theBground.height/2;
		
		closeButton.show(); 
		
	}

	
	public void drawMessage(){
		if(isLargeMessage == true){
			yPos = theAppProfile.theHeight/2 - theBgroundLg.height/2;
			closeButton.setPosition(theAppProfile.theWidth/2 -theBground.width/2 + theBground.width- closeImg.width/2, theAppProfile.theHeight/2 - theBgroundLg.height/2 - closeImg.height/2);
			/// draw background
			pApp.image(theBgroundLg, theAppProfile.theWidth/2 -theBground.width/2, theAppProfile.theHeight/2 - theBgroundLg.height/2);
			pApp.image(xtraImage, xPos + theBgroundLg.width/2 - xtraImage.width/2, yPos + theMargin);
			/// draw text
			pApp.textFont(headerFont, 18);
			pApp.fill(headerColor);
			pApp.text(headerText,  xPos + theMargin, yPos + theMargin *2  + xtraImage.height, theBgroundLg.width - theMargin, theAppProfile.theHeight);
			
			pApp.fill(bodyColor);
			pApp.textFont(bodyFont, 16);
			pApp.text(bodyText,  xPos + theMargin, yPos + theMargin * 4 + xtraImage.height, theBgroundLg.width - theMargin*2f, theAppProfile.theHeight);
			
		} else if(isLargeMessage == false){
			yPos = theAppProfile.theHeight/2 - theBground.height/2;
			closeButton.setPosition(theAppProfile.theWidth/2 -theBground.width/2 + theBground.width- closeImg.width/2, theAppProfile.theHeight/2 - theBground.height/2 - closeImg.height/2);
			
			/// draw background
			pApp.image(theBground, theAppProfile.theWidth/2 -theBground.width/2, theAppProfile.theHeight/2 - theBground.height/2);
			/// draw text
			pApp.textFont(headerFont, 18);
			pApp.fill(headerColor);
			pApp.text(headerText,  xPos + theMargin, yPos + theMargin, theBground.width - theMargin, theAppProfile.theHeight);
			
			pApp.fill(bodyColor);
			pApp.textFont(bodyFont, 16);
			pApp.text(bodyText,  xPos + theMargin, yPos + theMargin * 3, theBground.width - theMargin*2f, theAppProfile.theHeight);
		}
		
		
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
