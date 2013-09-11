import processing.core.PApplet;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Tool;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.processing.LeapMotion;


public class Main extends PApplet{
	
	
	LeapMotion leapMotion;

	ConcurrentMap<Integer, Integer> fingerColors;
	ConcurrentMap<Integer, Integer> toolColors;
	ConcurrentMap<Integer, Vector> fingerPositions;
	ConcurrentMap<Integer, Vector> toolPositions;


	static float LEAP_WIDTH = 200.0f; // in mm
	static float LEAP_HEIGHT = 700.0f; // in mm

	public void setup(){
	  // size(16*50, 9*50);
	  size(800,600);
	  background(20);
	  frameRate(60);
	  ellipseMode(CENTER);

	  leapMotion = new LeapMotion(this);
	  fingerColors = new ConcurrentHashMap<Integer, Integer>();
	  toolColors = new ConcurrentHashMap<Integer, Integer>();
	  fingerPositions = new ConcurrentHashMap<Integer, Vector>();
	  toolPositions = new ConcurrentHashMap<Integer, Vector>();
	}

	public void draw(){
	  fill(0);
	  rect(0, 0, width, height);

	  for (Map.Entry entry : fingerPositions.entrySet()){
	    Integer fingerId = (Integer) entry.getKey();
	    Vector position = (Vector) entry.getValue();
	    fill(fingerColors.get(fingerId));
	    noStroke();
	    float tSize = map(position.getZ(), 0.0f, 100.0f, 20.0f ,0.0f);
	     println(position.getZ());
	    ellipse(leapToScreenX(position.getX()), leapToScreenY(position.getY()), tSize, tSize);
	  }
	}
	
  void onFrame(final Controller controller){
	  
	  Frame frame = controller.frame();
	  
	  for (Finger finger : frame.fingers()){
	    println("FINGER ID: " + frame.fingers());
	    println("Hand has " + frame.fingers().count());
	    
	    int fingerId = finger.id();
	    int c = color(random(0, 255), random(0, 255), random(0, 255));  //generate random color
	    fingerColors.putIfAbsent(fingerId, c);  //if there isn't a color put color into fingerColors at fingerId
	    fingerPositions.put(fingerId, finger.tipPosition());
	  }
	  
	  for (Tool tool : frame.tools()){
	    int toolId = tool.id();
	    int c = color(random(0, 255), random(0, 255), random(0, 255));
	    toolColors.putIfAbsent(toolId, c);
	    toolPositions.put(toolId, tool.tipPosition());
	  }

	  // todo:  clean up expired finger/toolIds
	}


	//// this lerps the positions

	float leapToScreenX(float x)
	{
	  float c = width / 2.0f;
	  if (x > 0.0)
	  {
	    return lerp(c, width, x/LEAP_WIDTH);
	  }
	  else
	  {
	    return lerp(c, 0.0f, -x/LEAP_WIDTH);
	  }
	}

	float leapToScreenY(float y)
	{
	  return lerp(height, 0.0f, y/LEAP_HEIGHT);
	}

}
/// end class
