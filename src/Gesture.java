import ddf.minim.Minim;
import processing.core.*;

public class Gesture extends PApplet{
	
	int id;
	String state;
	PVector position;
	PVector direction;
	float speed;

	
	private static Gesture instance = null;
	
	protected Gesture() {
	      // Exists only to defeat instantiation.

	    
	}
	
	void initGesture(){
	
		
	}
	
	public static Gesture getInstance() {
      if(instance == null) {
         instance = new Gesture();
      }
      return instance;
    }
	



}
