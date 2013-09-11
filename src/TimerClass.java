import processing.core.*;
// This class based on code found here: http://www.goldb.org/stopwatchjava.html

class TimerClass {
	int startTime = 0, stopTime = 0;
	boolean running = false;  
  

	PApplet pApp;
	AppProfile theAppProfile;
	  
  
	
	TimerClass(){
		theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;
	}
    
    void start() {
        startTime = pApp.millis();
        running = true;
    }
    void stop() {
        stopTime =  pApp.millis();
        running = false;
    }
    int getElapsedTime() {
        int elapsed;
        if (running) {
             elapsed = ( pApp.millis() - startTime);
        }
        else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }
    int milisecond() {
        return (getElapsedTime() / 10) % 60;
    }
    int second() {
      return (getElapsedTime() / 1000) % 60;
    }
    int minute() {
      return (getElapsedTime() / (1000*60)) % 60;
    }
    int hour() {
      return (getElapsedTime() / (1000*60*60)) % 24;
    }
}
