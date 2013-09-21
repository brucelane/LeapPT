import processing.core.*;
import java.util.Vector;

public class GestureProfile extends PApplet{
	private static GestureProfile instance = null;
	int id;
	String state;
	PVector position;
	PVector direction;
	float speed;


	
	protected GestureProfile() {
	      // Exists only to defeat instantiation.

	    
	}
	
	void initGesture(){
	
		
	}
	
	public static GestureProfile getInstance() {
      if(instance == null) {
         instance = new GestureProfile();
      }
      return instance;
    }
	



}
