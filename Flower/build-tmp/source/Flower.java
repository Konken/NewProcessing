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

public class Flower extends PApplet {

float x,y;
float noiseX,noiseY;
float angle;
float noiseAngle;
float rad=300;
public void setup() {
	
	background(0);
	x=width/2;
	y=height/2;
	noiseX=random(10);
	noiseY=random(10);
	angle=0;
	noiseAngle=random(10);
}

public void draw() {
	stroke(255,10);
	angle+=(noise(noiseAngle));
	float tx1=cos(radians(angle))*rad;
	float ty1=sin(radians(angle))*rad;
	float tx2=cos(radians(180+angle))*rad;
	float ty2=sin(radians(180+angle))*rad;
	line(x+tx1,y+ty1,x+tx2,y+ty2);
	noiseX+=0.5f;
	noiseY+=0.5f;
	noiseAngle+=0.05f;
	rad=noise(noiseX)*3000;
}
  public void settings() { 	fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Flower" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
