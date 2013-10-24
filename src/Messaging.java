import java.util.ArrayList;

import processing.core.*;
import controlP5.*; /// may have to remove this

public class Messaging implements ControlListener{
	
	 
	 @SuppressWarnings("deprecation")
	
	 private static Messaging instance = null;

	 PApplet pApp;
	 AppProfile theAppProfile;
	 
	 PlayerProfile thePlayerProfile;
	 
	 PImage bgroundImg;
	 PImage tabMenuImg;
	 PImage tabAboutImg;
	 PImage tabStatsImg;
	 PImage tabSettingsImg;
	 PImage gameThumbImg;
	 
	 /// navigation images
	 PImage aboutBtImg;
	 PImage gamesBtImg;
	 PImage progBtImg;
	 PImage settBtImg;
	 PImage aboutBtImgR;
	 PImage gamesBtImgR;
	 PImage progBtImgR;
	 PImage settBtImgR;
	 //
	 PImage closeImg;
	 PImage closeImgR;
	 PImage playGameImg;
	 PImage playGameImgR;

	 String bgroundImgPath = "data/message_bground.png";
	 String tabAbtImgPath = "data/tab_about.png";
	 String tabMenuImgPath = "data/tab_game_menu.png";
	 String tabStatsImgPath = "data/tab_stats.png";
	 String tabSettgsImgPath = "data/tab_settings.png";
	 String closeImgPath = "data/close_button.png";
	 String gameThumbPath;
	 
	 ///// fonts
	 PFont HeaderFont; /// normal fonts
	 PFont BodyFont; // use true/false for smooth/no-smooth for Control fonts
	 PFont statFont;
	 PFont ButtonFont;
	 ControlFont controlPFont;
	 ControlFont navPFont;
	 
	 /// color for nav fonts
	 int blueNav;
	 int whiteNav;
	 //// interface elements
	 ControlP5 cp5;
	 ControlP5 ControlEvent;
	 
	 // general ui
	 Button closeButton;
	 Button aboutButton;
	 Button statsButton;
	 Button gamesButton;
	 Button settingsButton;
	 
	 // menu ui
	 Button game0Button;
	 Button game1Button;
	 Button game2Button;
	 Button game3Button;
	 Button game4Button;
	 Button game5Button;
	 
	 // settings menu ui
	 Button saveGameButton;
	 Button difficultyButton;
	 Button helpButton;
	 Button resetButton;

	 
	 Button playButton;
	 //
	 Textarea statTextArea;
	 Textarea gameInfoTextArea;
	 Textarea aboutTextArea;
	 // 
	 boolean showGameInfo = false;
	 
     String curStatHeader = "SHOWING DATA FOR: ";
     String statData = "";
     String gameInfoData = "";
     
     ///
     int newGameID = 0;

	 //
	 String messageState = "showMenu"; /// default
	 /// margins and positioning
	 float theMargin = 20;
	 float theMargin2 = 40;
	 float textX; /// text box positioning
	 float textY;
	 float textWidth;
	 float textHeight;
	 float bgX; /// background positioning 
	 float bgY;
	 
	 float chvDispX; /// positioning of cheevos
	 float chvDispY;
	 float chivHeight;
	 
	 float gameInfoThumbX; //positioning of game info thumb
	 float gameInfoThumbY;
	 
	 String theHeader;
	 String theMessage;
	 //
	 ArrayList<Achievment> PlayerAchievments;
	 
	 //
	 TimerClass theTimer;

	 Messaging() {
	     
		 theAppProfile = theAppProfile.getInstance();
		 pApp = theAppProfile.pApp;
		 
		 thePlayerProfile = thePlayerProfile.getInstance();
		 
		 tabAboutImg = pApp.loadImage(tabAbtImgPath);
		 tabMenuImg = pApp.loadImage(tabMenuImgPath);
		 tabStatsImg = pApp.loadImage(tabStatsImgPath);
		 tabSettingsImg = pApp.loadImage(tabSettgsImgPath);

		 closeImg = pApp.loadImage("data/close_button.png");
		 closeImgR = pApp.loadImage("data/close_buttonR.png");
		 
		 playGameImg = pApp.loadImage("data/butt_playGame.png");
		 playGameImgR = pApp.loadImage("data/butt_playGameR.png");
		 
		 /// main nav images
		 aboutBtImg = pApp.loadImage("data/butt_about.png");
		 gamesBtImg = pApp.loadImage("data/butt_games.png");
		 progBtImg = pApp.loadImage("data/butt_progress.png");
		 settBtImg = pApp.loadImage("data/butt_settings.png");
		 aboutBtImgR = pApp.loadImage("data/butt_aboutR.png");
		 gamesBtImgR = pApp.loadImage("data/butt_gamesR.png");
		 progBtImgR = pApp.loadImage("data/butt_progressR.png");
		 settBtImgR = pApp.loadImage("data/butt_settingsR.png");
		 
		 
		 bgX = theAppProfile.theWidth/2 - tabMenuImg.width/2;		 
		 bgY = theAppProfile.theHeight/2 - tabMenuImg.height/2;
		 
		 //// cheevo positioning
		 chvDispX = bgX + tabMenuImg.width/2;	
		 chvDispY = bgY + theAppProfile.theHeight/2 - tabMenuImg.height/2;
		 chivHeight = 45;
		 
		 /// game info thumb pic
		 gameInfoThumbX =  bgX + tabMenuImg.width - 215 - theMargin2;	
		 gameInfoThumbY =  bgY + theMargin2 * 2 + theMargin;
		 
		 HeaderFont = pApp.createFont("Neutra Text",22, true); /// normal fonts
		 BodyFont = pApp.createFont("Neutra Text",18, true); // use true/false for smooth/no-smooth for Control fonts
		 statFont = pApp.createFont("Neutra Text",14, true);
		 ButtonFont = pApp.createFont("Neutra Text",  22, true);
		 cp5 = new ControlP5(pApp);
		 
		 navPFont = new ControlFont(BodyFont, 241);
		 controlPFont = new ControlFont(ButtonFont,241);
		 
		 blueNav = pApp.color(0,123,234);
		 whiteNav = pApp.color(255,255,255);
		 theTimer = new TimerClass();
		 
		 initGUI();
		 loadPlayerAchievements();
		 
	 }

	 public void closeMessage(){

		 hideGameMenuButtons();
		 hideSettingsMenuButtons();
		 closeButton.hide(); 
		 gamesButton.hide();
		 statsButton.hide();
		 settingsButton.hide();
		 statTextArea.hide();  
		 gameInfoTextArea.hide();
		 aboutButton.hide();
	 }
	 
	 public void showBasicInterface(){
		
		///  pApp.image(bgroundImg, bgX, bgY);
		 //// display trim

	 }
	 
	 
	 public void drawMessageBox(){
		 //// display trim
		 
		 if(messageState == "default"){
			 
			 /// do default();
			 
		 } else if (messageState == "message"){
			 
			 // show message
			  
		 } else if (messageState == "winMessage"){
			 
			 // showWin();
			  
		 } else if (messageState == "loseMessage"){ 
			 
			 // showLose();
			 
		 } else if (messageState == "showAbout"){ 
			 
			 pApp.image(tabAboutImg, bgX, bgY);
			 
		 } else if (messageState == "showStats"){
			
			 
			 pApp.image(tabStatsImg, bgX, bgY);
			 showAchievments();
			 
		 } else if (messageState == "showMenu"){
			 
			 /// showGameMenu();
			 pApp.image(tabMenuImg, bgX, bgY);
			 showGameInfoPic();
			 
		 } else if (messageState == "settings"){
			 
			 /// showGameMenu();
			 pApp.image(tabSettingsImg, bgX, bgY);
			 
		 }	else if (messageState == "showGameInfo"){
			 
			 /// showGameMenu();
			 pApp.image(tabMenuImg, bgX, bgY);
			 showGameInfoPic();
			 
		 } else {
			 pApp.println("BAD MENU PARAM");
			 showGameMenu();
		 }

		 /// pApp.text(tHead, bgX + theMargin, bgY + theMargin);
		 // pApp.text(tMess, bgX + theMargin, bgY + theMargin + 50);
	 }
	  
	 ///////////////////////////////////////////////
	 /////// SHOW and HIDE CONFIGURATIONS ////////
	 //////////////////////////////////////
	 
	 public void showWin(){
		 // buttons
		 showMainMenuButtons();
		 
		 /// text
		 statTextArea.hide();  
		 

	 }
	 
	 public void showLose(){
		 // buttons
		 showMainMenuButtons();
		 
		 /// text
		 statTextArea.hide();    
	 }
	 
	 public void showAbout(){
		 
		 messageState = "showAbout";		 
		 /// activate button style
		 
		  cp5.getController("ABOUT")
		  .setColorBackground(pApp.color(125,125,125,165))
		  ;
		 
		  cp5.getController("GAMES")
		  .setColorBackground(pApp.color(200,200,200,0))
		  ;
		  
		  cp5.getController("PROGRESS")
		  .setColorBackground(pApp.color(200,200,200,0))
		  ;
		  
		  cp5.getController("SETTINGS")
		  .setColorBackground(pApp.color(200,200,200,0))
		  ;
		  
		 // buttons
		  showMainMenuButtons();
		 //		 
		 hideSettingsMenuButtons();
		 hideGameMenuButtons();
		 showMainMenuButtons();
		 
		 /// show and hide text
		 aboutTextArea.show();
		 statTextArea.hide();
		 gameInfoTextArea.hide();
		 
		 
	 }
	 public void showStats(){
		 setStats();
		 
		 messageState = "showStats";
		 
		 /// activate button style
		 cp5.getController("ABOUT")
		  .setColorBackground(pApp.color(200,200,200,0))
		  ;
		 
		  cp5.getController("GAMES")
		  .setColorBackground(pApp.color(200,200,200,0))
		  ;
		  cp5.getController("PROGRESS")
		  .setColorBackground(pApp.color(125,125,125,165))
		  ;
		  cp5.getController("SETTINGS")
		  .setColorBackground(pApp.color(200,200,200,0))
		  ;
		  
		 // buttons
		  showMainMenuButtons();
		 //
		 
		 hideSettingsMenuButtons();
		 hideGameMenuButtons();

		 /// show and hide text
		 aboutTextArea.hide();
		 statTextArea.show();
		 gameInfoTextArea.hide();
	 }
	 
	 public void showGameMenu(){
		 messageState = "showMenu";
		
		 
		 /// do button styles
		 /// activate button style
		 cp5.getController("ABOUT")
		  .setColorBackground(pApp.color(200,200,200,0))

		  ;
		 
		  cp5.getController("GAMES")
		  .setColorBackground(pApp.color(125,125,125,165))

		  ;
		  cp5.getController("PROGRESS")
		  .setColorBackground(pApp.color(200,200,200,0))

		  ;
		  
		  cp5.getController("SETTINGS")
		  .setColorBackground(pApp.color(200,200,200,0))

		  ;
		 
		 hideSettingsMenuButtons();
		 showGameMenuButtons();
		 showMainMenuButtons();

		 /// text
		 aboutTextArea.hide();
		 statTextArea.hide();   
		 gameInfoTextArea.show();
	 }
	 
	 public void showMainMenuButtons(){
		 // buttons
		 closeButton.show();
		 aboutButton.show();
		 gamesButton.show();
		 statsButton.show();
		 settingsButton.show();	
		 
	 }
	 
	 public void showGameMenuButtons(){

		 game0Button.show();
		 game1Button.show();
		 game2Button.show();
		 game3Button.show();
		 game4Button.show();
		 game5Button.show();
		 playButton.show();

	 }
	 

	 public void hideGameMenuButtons(){
		 
		 game0Button.hide();
		 game1Button.hide();
		 game2Button.hide();
		 game3Button.hide();
		 game4Button.hide();
		 game5Button.hide();
		 playButton.hide();
		 
	 }
	 
	 public void showSettings(){
		 messageState = "settings";
		
		 /// do button styles
		 /// activate button style 
		 
		 cp5.getController("ABOUT")
		  .setColorBackground(pApp.color(200,200,200,0))

		  ;
		 
		  cp5.getController("GAMES")
		  .setColorBackground(pApp.color(200,200,200,0))

		  ;
		  cp5.getController("PROGRESS")
		  .setColorBackground(pApp.color(200,200,200,0))
		  ;
		 
		  cp5.getController("SETTINGS")
		  .setColorBackground(pApp.color(125,125,125,165))

		  ;
		  
		 showMainMenuButtons();
		 showSettingsMenuButtons();
		 hideGameMenuButtons();

		 /// text
		 aboutTextArea.hide();
		 statTextArea.hide();  
		 gameInfoTextArea.hide();
	 }

	 
	 public void showSettingsMenuButtons(){
		 saveGameButton.show();
		 difficultyButton.show();
		 helpButton.show();
		 resetButton.show();

	 }
	 public void hideSettingsMenuButtons(){
		 saveGameButton.hide();
		 difficultyButton.hide();
		 helpButton.hide();
		 resetButton.hide();

	 }
	 
	 public void showGameInfo(int tID){
		
		 gameInfoData = "";
		 newGameID = tID;
		 /// show popup background
		 try{
			 // show "more info" 
			 gameInfoData += thePlayerProfile.GameStats.get(tID).gameName + "\n" + "\n";
			 gameInfoData += thePlayerProfile.GameStats.get(tID).gameInfo + "\n";
			 /// gameInfoData += thePlayerProfile.GameStats.get(tID).gameThumbnail + "\n";
			 /// gameInfoData += thePlayerProfile.GameStats.get(tID).gameMainPic + "\n";
			 gameThumbImg = pApp.loadImage(thePlayerProfile.GameStats.get(tID).gameThumbnail);
			 gameInfoTextArea.setText(gameInfoData);
			 
			 gameInfoTextArea.show();
		 
		 } catch (Exception e){
			 
			 pApp.println("game info error:  " + e);
		 }
		 /// display the info for the correct ID
		 
	 }
	 private void showGameInfoPic(){
		 try{
			 // show "more info" 
			 pApp.image(gameThumbImg, gameInfoThumbX, gameInfoThumbY);
			 
		 
		 } catch (Exception e){
			 
			 pApp.println("game image error:  " + e);
		 } 
	 }
	 
	 ///////// set achievments from player profile ////////////
	 
	 public void loadPlayerAchievements(){

		 // re init theachievment array in case
		 // there's new cheevos
		 
		 PlayerAchievments = new ArrayList();
		 /// get current player cheevos
		 
		 /// get the name of the player's current achievment
		 for(int i=0; i < thePlayerProfile.CheevoNames.size(); i++){
			 
			 String tCheevName = thePlayerProfile.CheevoNames.get(i);
			 

			 /// cross reference with all games
			 for(int j=0; j < thePlayerProfile.GameStats.size(); j++){
				 
				 /// cross reference with all cheevos in that game
				 for(int k=0; k < thePlayerProfile.GameStats.get(j).CheevoNames.size(); k++){
					 
					 if(tCheevName.equals(thePlayerProfile.GameStats.get(j).CheevoNames.get(k))){
						
						 String cheevDesc = thePlayerProfile.GameStats.get(j).CheevoDescription.get(k);
						 String cheevPath = thePlayerProfile.GameStats.get(j).CheevoImage.get(k);
						 
						 Achievment tCheevObj  = new Achievment(tCheevName, cheevDesc, cheevPath);
						 tCheevObj.tX = chvDispX;
						 tCheevObj.tY = chvDispY + (i*chivHeight);
						 PlayerAchievments.add(tCheevObj);
						 
					 }
					 
				 }
				 
			 }
			
			
		 }
		 showAchievments();
	 }
	 
	 public void showAchievments(){
		 
		 for(int i=0; i < PlayerAchievments.size(); i++){
			 Achievment tCheev = PlayerAchievments.get(i);
			 tCheev.displayCheevo();
		 }
		 
	 }
	 
	 public void setStats(){
		 try{

				/// showing stat header
			   curStatHeader = "SHOWING DATA FOR: ";
			    
			   statData = "";
			    /// go thru all the games
			   /// then display all the stats
			    for (int i=0; i< theAppProfile.gameMode.size(); i++){
			    	
			    	statData += "GAME NAME: " + thePlayerProfile.GameStats.get(i).gameName + "\n";
			    	/// pApp.println("Stat length: " + thePlayerProfile.statLength);
			    	for(int j=0; j<thePlayerProfile.statLength; j++){
			    		statData += "\n";	
		    			if(j==0){
		    				if(thePlayerProfile.GameStats.get(i).highScore < thePlayerProfile.GameStats.get(i).curScore){
		    					thePlayerProfile.GameStats.get(i).highScore = thePlayerProfile.GameStats.get(i).curScore;
		    				} 
		    				statData += "high score: " + thePlayerProfile.GameStats.get(i).highScore + "\n" + "\n";
		    				statData += "recent score: " + thePlayerProfile.GameStats.get(i).curScore + "\n";
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
			    	statData += "\n";

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
		 
		 	String theCleaner = "";
		 	statTextArea.setText(theCleaner);
		    statTextArea.setText(statData);
		 
	 }
	 
	 private void parseAboutText(){
		 String aboutData;
		 aboutData = "Visual Touch Therapy is a software program that uses the Leap Motion gesture controller as an interface for physical therapy training for people with difficulty using their motor skills, such as those with spinal cord injuries, head injuries, nerve damage, or stroke patients.";
		 aboutData += "\n\nIt uses conventional video game and sports games and imagery in conjunction with repetitive movement exercises as well as hand-eye co-ordination challenges, to provide a method of therapy that is rewarding and gives more immediate feedback than traditional methods.";
		 aboutData += "\n\nEach of these games utilize a different motion-- side to side, 'swipes', tap, push and pull.";
		 aboutData += "\n\nThe software platform is Processing/JAVA and the hardware is a traditional Leap Motion controller.";
		 
		 setAboutText(aboutData);
		 
	 }
	 
	 private void setAboutText(String aboutData){
		    aboutTextArea.setText(aboutData);
		 
		 
		 
	 }
	 
	 ///////// INTERFACE ELEMENTS

	public void initGUI(){
		 
		 
		 //// GENERAL UI //////////
		PImage[] closeMenuImg = {closeImg,closeImgR,closeImgR};
		 closeButton = cp5.addButton("CLOSE")
				  .setPosition(bgX + tabMenuImg.width - 60 - theMargin/2, bgY + theMargin/2)
				  .updateSize()
				  .setImages(closeMenuImg)
				  .updateSize()
			     
				  ;
				 
				 
				 closeButton.hide(); 
				 
		/////// SETTINGS WINDOW INTERFACE /////////

			saveGameButton = cp5.addButton("SAVE GAME")
					  .setPosition(bgX + tabMenuImg.width/2 - 150/2, bgY + theMargin2*4)
					  // .setImage(closeImg)
					  .setSize(150,40)
				      .setColorForeground(pApp.color(125,125,125,165))
				      .setColorBackground(pApp.color(200,200,200,35))
				      .setColorActive(pApp.color(255,35))
					  ;
					 
			difficultyButton = cp5.addButton("DIFFICULTY")
					  .setPosition(bgX + tabMenuImg.width/2 - 150/2, bgY + theMargin2*5)
					  // .setImage(closeImg)
					  .setSize(150,40)
				      .setColorForeground(pApp.color(125,125,125,165))
				      .setColorBackground(pApp.color(200,200,200,35))
				      .setColorActive(pApp.color(255,35))
					  ;
					 

			
			helpButton = cp5.addButton("HELP")
					  .setPosition(bgX + tabMenuImg.width/2 - 150/2, bgY + theMargin2*7)
					  // .setImage(closeImg)
					  .setSize(150,40)
				      .setColorForeground(pApp.color(125,125,125,165))
				      .setColorBackground(pApp.color(200,200,200,35))
				      .setColorActive(pApp.color(255,35))
					  ;
					 

			
			resetButton = cp5.addButton("RESET PROGRESS")
					  .setPosition(bgX + tabMenuImg.width/2 - 150/2, bgY + theMargin2*8)
					  // .setImage(closeImg)
					  .setSize(150,40)
				      .setColorForeground(pApp.color(125,125,125,165))
				      .setColorBackground(pApp.color(200,200,200,35))
				      .setColorActive(pApp.color(255,35))
					  ;
	 
		hideSettingsMenuButtons();	
		
		/// GAME MENU INTERFACE ///////

		PImage[] playGameImgs = {playGameImg,playGameImgR,playGameImgR};
		playButton = cp5.addButton("PLAY GAME")
				  .setPosition(bgX + tabMenuImg.width - theMargin -150, bgY + tabMenuImg.height - theMargin * 3)
				  .setImages(playGameImgs)
				  .updateSize()
				  ;

		/////// GAME BUTTONS ////////////////////////
		game0Button = cp5.addButton("GAME 0")
				  .setPosition(bgX + theMargin *2, bgY + theMargin2*4)
				  // .setImage(closeImg)
				  .setSize(150,40)
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,35))
			      .setColorActive(pApp.color(255,35))
				  ;
				 
		game1Button = cp5.addButton("GAME 1")
				  .setPosition(bgX + theMargin *2, bgY + theMargin2*5)
				  // .setImage(closeImg)
				  .setSize(150,40)
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,35))
			      .setColorActive(pApp.color(255,35))
				  ;
				 

		
		game2Button = cp5.addButton("GAME 2")
				  .setPosition(bgX + theMargin *2, bgY + theMargin2*6)
				  // .setImage(closeImg)
				  .setSize(150,40)
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,35))
			      .setColorActive(pApp.color(255,35))
				  ;
				 

		
		game3Button = cp5.addButton("GAME 3")
				  .setPosition(bgX + theMargin *2, bgY + theMargin2*7)
				  // .setImage(closeImg)
				  .setSize(150,40)
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,35))
			      .setColorActive(pApp.color(255,35))
				  ;
				 

		
		game4Button = cp5.addButton("GAME 4")
				  .setPosition(bgX + theMargin *2, bgY + theMargin2*8)
				  // .setImage(closeImg)
				  .setSize(150,40)
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,35))
			      .setColorActive(pApp.color(255,35))
				  ;
		
		game5Button = cp5.addButton("GAME 5")
				  .setPosition(bgX + theMargin *2, bgY + theMargin2*9)
				  // .setImage(closeImg)
				  .setSize(150,40)
			      .setColorForeground(pApp.color(125,125,125,165))
			      .setColorBackground(pApp.color(200,200,200,35))
			      .setColorActive(pApp.color(255,35))
				  ;		 

		
		hideGameMenuButtons();
		
		/// MAIN MESSAGING WINDOW INTERFACE ///////
		
		PImage[] aboutImgs = {aboutBtImg,aboutBtImgR,aboutBtImgR};
		PImage[] gamesImgs = {gamesBtImg,gamesBtImgR,gamesBtImgR};
		PImage[] progImgs = {progBtImg,progBtImgR,progBtImgR};
		PImage[] settImgs = {settBtImg,settBtImgR,settBtImgR};
		
		aboutButton = cp5.addButton("ABOUT")

				  .setPosition(bgX + theMargin, bgY + theMargin)
				  .setImages(aboutImgs)
				  .updateSize()
				  ;
		 
		aboutButton.hide();
		
		 gamesButton = cp5.addButton("GAMES")

				  .setPosition(bgX + theMargin*2 + 150, bgY + theMargin)
				  .setImages(gamesImgs)
				  .updateSize()
				  ;
				 
				 
		 gamesButton.hide(); 
		 

		 statsButton = cp5.addButton("PROGRESS")

				  .setPosition(bgX + theMargin*3 + 150*2, bgY + theMargin)
				  .setImages(progImgs)
				  .updateSize()
				  ;
		
		 
		 statsButton.hide(); 
		 settingsButton = cp5.addButton("SETTINGS")

				  .setPosition(bgX + theMargin*4 + 150*3, bgY + theMargin)
				  .setImages(settImgs)
				  .updateSize()
				  ;
		 settingsButton.hide();	 

		
		 
		 //// settings button
		 cp5.getController("SAVE GAME")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
		 
		 cp5.getController("DIFFICULTY")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
		 
		 cp5.getController("HELP")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
		 
		 cp5.getController("RESET PROGRESS")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
			 
		 
		 //// games button
		 cp5.getController("GAME 0")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
		 
		 cp5.getController("GAME 1")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
		 
		 cp5.getController("GAME 2")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
		 
		 cp5.getController("GAME 3")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
		 
		 cp5.getController("GAME 4")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
		 
		 cp5.getController("GAME 5")
		 .getCaptionLabel()
		 .setFont(navPFont)
		 .setSize(18)
		 ;
		 
		 /////// TEXT BOXES ////////////////
		 /// about text area
		 aboutTextArea = cp5.addTextarea("abouttext")
                 .setPosition(bgX + 38, bgY + 138)
                 .setSize(310,268)
                 .setFont(BodyFont)
                 .setLineHeight(18)
                 .setColor(pApp.color(255))
                 .setColorBackground(pApp.color(255,65))
                 .setColorForeground(pApp.color(255))
		 		 .setColorActive(pApp.color(115,176,230));
                 ;
                 
         aboutTextArea.hide();
		 //// stat window
		 statTextArea = cp5.addTextarea("stattext")
                 .setPosition(bgX + 20, bgY + 140)
                 .setSize(328,286)
                 .setFont(BodyFont)
                 .setLineHeight(14)
                 .setColor(pApp.color(255))
                 .setColorBackground(pApp.color(255,65))
                 .setColorForeground(pApp.color(255))
		 		 .setColorActive(pApp.color(230,190,139));
                 ;
                 
         statTextArea.hide();   
         /// statTextArea.disableColorBackground();
         /// statTextArea.showArrow();
         /// statTextArea.addDrawable(arg0)

         
         //// game info box window
         
         gameInfoTextArea = cp5.addTextarea("gameinfotext")
                 .setPosition(bgX + 215, bgY + 100)
                 .setSize(275,286)
                 .setFont(BodyFont)
                 .setLineHeight(20)
                 .setColor(pApp.color(255))
                 .setColorBackground(pApp.color(255,0))
                 .setColorForeground(pApp.color(255))
                 .setColorActive(pApp.color(230,190,139));
                 ;
                 
          gameInfoTextArea.hide();     
         
			
          
          
          
          ////// INIT ABOUT TEXT. SAVE THIS FOR LAST SINCE 
          ////// it only needs to happen once
          parseAboutText();
	 }
	 
	 /// I shouldn't need this since it's listening in the main
	 /// class, but whatevs
	 public void controlEvent(ControlEvent theEvent) {
		
		 
	}
   
	 
	 

	 
} /// end class
