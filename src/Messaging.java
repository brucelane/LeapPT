import java.util.ArrayList;

import processing.core.*;
import controlP5.*; /// may have to remove this

public class Messaging implements ControlListener{
	
	 private static Messaging instance = null;

	 PApplet pApp;
	 AppProfile theAppProfile;
	 
	 PlayerProfile thePlayerProfile;
	 
	 PImage bgroundImg;
	 PImage tabLImg;
	 PImage tabRImg;
	 PImage closeImg;
	 
	 String bgroundImgPath = "data/message_bground.png";
	 String tabLImgPath = "data/tab_left.png";
	 String tabRImgPath = "data/tab_right.png";
	 String closeImgPath = "data/close_button.png";
	 
	 ///// fonts
	 PFont HeaderFont; /// normal fonts
	 PFont BodyFont; // use true/false for smooth/no-smooth for Control fonts
	 PFont statFont;
	 
	 //// interface elements
	 ControlP5 cp5;
	 ControlP5 ControlEvent;
	 
	 // general ui
	 Button closeButton;
	 Button statsButton;
	 Button menuButton;
	 
	 // menu ui
	 Button game0Button;
	 Button game1Button;
	 Button game2Button;
	 Button game3Button;
	 Button game4Button;
	 
	 Button playButton;
	 //
	 Textarea statTextArea;
	 Textarea gameInfoTextArea;
	 // 
	 boolean showGameInfo = false;
	 
     String curStatHeader = "SHOWING DATA FOR: ";
     String statData = "";
     String gameInfoData = "";

	 //
	 String messageState = "showMenu"; /// default
	 /// margins and positioning
	 float theMargin = 20;
	 float textX;
	 float textY;
	 float textWidth;
	 float textHeight;
	 float bgX;
	 float bgY;
	 
	 String theHeader;
	 String theMessage;
	 
	 
	 
	 Messaging() {
	     
		 theAppProfile = theAppProfile.getInstance();
		 pApp = theAppProfile.pApp;
		 
		 thePlayerProfile = thePlayerProfile.getInstance();
		 
		 bgroundImg = pApp.loadImage(bgroundImgPath);
		 
		 tabLImg = pApp.loadImage(tabLImgPath);
		 tabRImg = pApp.loadImage(tabRImgPath);
		 
		 closeImg = pApp.loadImage(closeImgPath);
		 
		 bgX = theAppProfile.theWidth/2 - bgroundImg.width/2;		 
		 bgY = theAppProfile.theHeight/2 - bgroundImg.height/2;
		 
		 HeaderFont = pApp.createFont("Neutra Text",22, true); /// normal fonts
		 BodyFont = pApp.createFont("Neutra Text",18, true); // use true/false for smooth/no-smooth for Control fonts
		 statFont = pApp.createFont("Neutra Text",14, true);
		 
		 cp5 = new ControlP5(pApp);
		 
		 initGUI();
		 
	 }
	 
	
	 
	 public void showMessageBox(){

		 if(messageState == "message"){
			 
			 showMessage();
			 
		 } else if (messageState == "winMessage"){
			 
			 showWin();
			  
		 } else if (messageState == "loseMessage"){ 
			 
			 showLose();
			 
		 } else if (messageState == "showStats"){
			 
			 showStats();
			 
		 } else if (messageState == "showMenu"){
			 
			 showGameMenu();
			 
		 } else {
			 pApp.println("BAD MENU PARAM");
			 showGameMenu();
		 }
	 }
	 
	 public void closeMessage(){
		 //// hide menu buttons
		 game0Button.hide();
		 game1Button.hide();
		 game2Button.hide();
		 game3Button.hide();
		 game4Button.hide();
		 
		 statTextArea.hide();  
		 gameInfoTextArea.hide();
		 closeButton.hide(); 
		 menuButton.hide();
		 statsButton.hide();
	 }
	 
	 public void showBasicInterface(){
		 
		 //// hide menu buttons
		 /*
		 game0Button.hide();
		 game1Button.hide();
		 game2Button.hide();
		 game3Button.hide();
		 game4Button.hide();
		 */
		 
		 pApp.image(bgroundImg, bgX, bgY);
		 //// display trim
		 
		 /// draw message
		 
		 /// pApp.text(tHead, bgX + theMargin, bgY + theMargin);
		 // pApp.text(tMess, bgX + theMargin, bgY + theMargin + 50);
		 
		 closeButton.show();
		 menuButton.show();
		 statsButton.show();
	 }
	 
	 
	 public void showMessage(){
		 
		 statTextArea.hide();    
		 //// draw background
		 bgX = theAppProfile.theWidth/2 - bgroundImg.width/2;		 
		 bgY = theAppProfile.theHeight/2 - bgroundImg.height/2;
		 pApp.image(bgroundImg, bgX, bgY);
		 //// display trim
		 
		 /// is showing info about a game?
		 
		 if(showGameInfo){
			 
			 /// do game popup bground
			 
			 
			 /// show game bground box
			 
			 /// pass game id to help class
			 
			 /// show it!

			 
		 } 
		 
		 /// draw message
		 
		 /// pApp.text(tHead, bgX + theMargin, bgY + theMargin);
		 // pApp.text(tMess, bgX + theMargin, bgY + theMargin + 50);
		 
		 closeButton.show();
		 menuButton.show();
		 statsButton.show();
	 
		 
	 }
	 
	 public void showWin(){
		 
		 statTextArea.hide();    
		 showBasicInterface();
		 
	 }
	 
	 public void showLose(){
		 
		 showBasicInterface();
	 }
	 
	 public void setStats(){
		 try{

			 /*
			  * 
			  * String gameName;
				int highScore;
				int curScore;
				int timeSpent;
				float percentComp;
				int numWins;
				int numLosses;
				*/
				/// showing stat header
			   curStatHeader = "SHOWING DATA FOR: ";
			    
			    /// go thru all the games
			   /// then display all the stats
			    for (int i=0; i< theAppProfile.gameMode.size(); i++){
			    	
			    	statData += "GAME NAME: " + thePlayerProfile.GameStats.get(i).gameName + "\n";
			    	/// pApp.println("Stat length: " + thePlayerProfile.statLength);
			    	for(int j=0; j<thePlayerProfile.statLength; j++){
			    		statData += "\n";	
		    			if(j==0){
		    				statData += "high score: " + thePlayerProfile.GameStats.get(i).highScore + "\n";
						}
						if(j ==1){
							statData += "time spent: " + thePlayerProfile.GameStats.get(i).timeSpent + "\n";

						}
						if(j ==2){
							statData += "percent completed: " + thePlayerProfile.GameStats.get(i).percentComp + "%" + "\n";
							
						}
						if(j ==3){
							statData += "number of wins: " + thePlayerProfile.GameStats.get(i).numWins + "\n";
							
						}
						if(j ==4){
							statData += "number of losses: " + thePlayerProfile.GameStats.get(i).numLosses + "\n";
							
						}
						if(j ==5){
							statData += "current level: " + thePlayerProfile.GameStats.get(i).curLevel + "\n";
						}
			    	}

			    }
			    
			    statData += "\n" + "\n";
			    /// pApp.println(statData);
			    /*
			    pApp.textFont(statFont);
			    pApp.text(statData, bgX + theMargin * 3, bgY + theMargin * 3);
			    */

				} catch (Exception e){
					pApp.println("stat error: " + e);
			}
		 
		    statTextArea.setText(statData);
		 
	 }
	 
	 
	 public void showStats(){
		 
		 showBasicInterface();
		 
		 pApp.image(tabRImg, bgX + theMargin*2, bgY + theMargin *3);
		 
		 /// show badges class
		 
		 statTextArea.show();
		 gameInfoTextArea.hide();
	 }
	 
	 public void showGameMenu(){
		 showBasicInterface();
		 
		 statTextArea.hide();    
		 
		 pApp.image(tabLImg, bgX + theMargin *2, bgY + theMargin *3);
		 
		 /// do stat button
		 
		 /// do menu button
		 
		 /// game buttons
		
		 
	 }
	 
	 public void showGameMenuButtons(){

		 game0Button.show();
		 game1Button.show();
		 game2Button.show();
		 game3Button.show();
		 game4Button.show();

	 }

	 public void hideGameMenuButtons(){
		 
		 game0Button.hide();
		 game1Button.hide();
		 game2Button.hide();
		 game3Button.hide();
		 game4Button.hide();
		 
	 }
	 
	 public void showGameInfo(int tID){
		 pApp.println("SHOW GAME INFO FOR: " + tID);
		 gameInfoData = "";
		 /// show popup background
		 try{
			 // show "more info" 
			 /*
			 pApp.println("Game Name: " + thePlayerProfile.GameStats.get(tID).gameName);
			 pApp.println("Game Info: " + thePlayerProfile.GameStats.get(tID).gameInfo);	 
			 pApp.println("Game Thumbnail: " + thePlayerProfile.GameStats.get(tID).gameThumbnail);
			 pApp.println("Game Thumbnail: " + thePlayerProfile.GameStats.get(tID).gameMainPic);
			 */
			 gameInfoData += thePlayerProfile.GameStats.get(tID).gameName + "\n" + "\n";
			 gameInfoData += thePlayerProfile.GameStats.get(tID).gameInfo + "\n";
			 gameInfoData += thePlayerProfile.GameStats.get(tID).gameThumbnail + "\n";
			 gameInfoData += thePlayerProfile.GameStats.get(tID).gameMainPic + "\n";
			 
			 gameInfoTextArea.setText(gameInfoData);
			 
			 gameInfoTextArea.show();
		 
		 } catch (Exception e){
			 
			 pApp.println("game info error:  " + e);
		 }
		 /// display the info for the correct ID
		 
	 }
	 
	 
	 ///////// INTERFACE ELEMENTS
	 
	 public void initGUI(){
		 
		 
		 //// GENERAL UI //////////
		 
		 closeButton = cp5.addButton("CLOSE")
				  .setPosition(bgX + bgroundImg.width - theMargin *4, bgY + theMargin)
				  .updateSize()
				  .setImage(closeImg)
				  .updateSize()
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,165))
			      .setColorActive(pApp.color(200,200,200,165))
				  ;
				 
				 
				 closeButton.hide(); 
		 
		 
		/// MENU WINDOW INTERFACE ///////
				 
		playButton = cp5.addButton("PLAY BUTTON")
				  .setPosition(bgX + theMargin *4, bgY + theMargin*12)
				  // .setImage(closeImg)
				  .updateSize()
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(255,0,0,165))
			      .setColorActive(pApp.color(200,200,200,165))
				  ;
		
		

		game0Button = cp5.addButton("GAME0")
				  .setPosition(bgX + theMargin *4, bgY + theMargin*7)
				  // .setImage(closeImg)
				  .updateSize()
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,165))
			      .setColorActive(pApp.color(200,200,200,165))
				  ;
				 
		game1Button = cp5.addButton("GAME1")
				  .setPosition(bgX + theMargin *4, bgY + theMargin*8)
				  // .setImage(closeImg)
				  .updateSize()
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,165))
			      .setColorActive(pApp.color(200,200,200,165))
				  ;
				 

		
		game2Button = cp5.addButton("GAME2")
				  .setPosition(bgX + theMargin *4, bgY + theMargin*9)
				  // .setImage(closeImg)
				  .updateSize()
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,165))
			      .setColorActive(pApp.color(200,200,200,165))
				  ;
				 

		
		game3Button = cp5.addButton("GAME3")
				  .setPosition(bgX + theMargin *4, bgY + theMargin*10)
				  // .setImage(closeImg)
				  .updateSize()
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,165))
			      .setColorActive(pApp.color(200,200,200,165))
				  ;
				 

		
		game4Button = cp5.addButton("GAME4")
				  .setPosition(bgX + theMargin *4, bgY + theMargin*11)
				  // .setImage(closeImg)
				  .updateSize()
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,165))
			      .setColorActive(pApp.color(200,200,200,165))
				  ;
				 

		
		hideGameMenuButtons();
		

		/// STAT WINDOW INTERFACE ///////

		 statsButton = cp5.addButton("STATS")

				 /// .setValue(20)
				  .setPosition(bgX + bgroundImg.width/2 + theMargin *2, bgY + theMargin*3.5f)
				  .updateSize()
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,165))
			      .setColorActive(pApp.color(200,200,200,165))
				  ;
				 
				 
		 statsButton.hide(); 
				 
		 menuButton = cp5.addButton("MENU")

				 /// .setValue(20)
				  .setPosition(bgX + theMargin *3, bgY + theMargin*3.5f)
				  .updateSize()
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,165))
			      .setColorActive(pApp.color(200,200,200,165))
				  ;
				 
				 
		 menuButton.hide(); 
		 
		 
		 /////// TEXT BOXES ////////////////
		 
		 //// stat window
		 statTextArea = cp5.addTextarea("stattext")
                 .setPosition(bgX + theMargin * 4, bgY + theMargin * 6)
                 .setSize(400,200)
                 .setFont(statFont)
                 .setLineHeight(14)
                 .setColor(pApp.color(255))
                 .setColorBackground(pApp.color(255,100))
                 .setColorForeground(pApp.color(255,100));
                 ;
                 
         statTextArea.hide();   
         
         
         //// text box window
         
         gameInfoTextArea = cp5.addTextarea("gameinfotext")
                 .setPosition(bgX + bgroundImg.width/2, bgY + theMargin * 6)
                 .setSize(300,200)
                 .setFont(statFont)
                 .setLineHeight(14)
                 .setColor(pApp.color(255))
                 .setColorBackground(pApp.color(255,100))
                 .setColorForeground(pApp.color(255,100));
                 ;
                 
          gameInfoTextArea.hide();     
         
				 
	 }
	 
	 /// I shouldn't need this since it's listening in the main
	 /// class, but whatevs
	 public void controlEvent(ControlEvent theEvent) {
		
		 
	}
   
	 
	 

	 
} /// end class
