// package src;

import processing.core.PApplet;

import java.awt.Polygon;

///////
class Poly extends PApplet{

	PApplet pApp;
	AppProfile theAppProfile;
	
	int npoints;
	float[]xpoints;
	float[]ypoints;
	
	public Poly(float[] x, float[] y, int n){
		theAppProfile =  theAppProfile.getInstance();
    	pApp = theAppProfile.pApp;
    	
    	xpoints = x;
    	ypoints = y;
    	npoints = n;
    	
		/// pApp.super(x, y, n);
	}
	
	void drawPoly(float[] x, float[] y, int n){
		xpoints = x;
    	ypoints = y;
    	npoints = n;
		
		try{
			pApp.beginShape();
			
			for(int i = 0; i<npoints; i++){
				
				pApp.vertex(xpoints[i],ypoints[i]);
			}
			
			pApp.endShape();
			
		} catch (Exception e){
			pApp.println("Poly exception: " + e);
		}
	}
}
