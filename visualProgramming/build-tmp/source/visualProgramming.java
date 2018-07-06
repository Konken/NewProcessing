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

public class visualProgramming extends PApplet {

Brush b;

public void setup() {
	
	translate(width/2,height/2);
	b=new Brush(0,0,random(360),200,40,600);
	background(0);
	stroke(255);
	b.drawBrush();
}

public void draw() {
	
}

class Brush{
	float x,y,ang,len,howMany,howLong;
	float sx=random(10);
	float sy=random(10);
	float ny=sy;
	Brush(float x,float y,float ang,float len,float howMany,float howLong){
		this.x=x;
		this.y=y;
		this.ang=ang;
		this.len=len+random(-5,5);
		this.howMany=howMany;
		this.howLong=howLong;
	}
	public void drawBrush(){
		pushMatrix();
		translate(x,y);
		rotate(radians(ang));
		for(int i=0;i<howMany;i++){
			ny=sy;
			float x1=len+random(-30,30);
			float y1=random(-30,30);
			float oldx=x1;
			float oldy=y1;
			for(int j=0;j<howLong;j++){
				x1-=len/howLong*2;
				y1+=(0.5f-noise(ny));
				ny+=0.008f;
				strokeWeight(5);
				line(x1,y1,oldx,oldy) ;
				oldx=x1;
				oldy=y1;
			}
		}
		popMatrix();
	}
}
  public void settings() { 	fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "visualProgramming" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
