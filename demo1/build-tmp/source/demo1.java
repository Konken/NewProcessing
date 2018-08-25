import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class demo1 extends PApplet {

float rad;
public void setup() {
	
	rad=300;
}

public void draw() {
	background(0);
	translate(width/2,height/2);
	for(int i=0;i<360;i++){
		fill(255);
		noStroke();
		float r=rad/(2*sin(PI/6));
		ellipse(cos(radians(i))*r,sin(radians(i))*r,10,10);
	}
}
  public void settings() { 	fullScreen(2); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#EA0202", "--stop-color=#E81C1C", "demo1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
