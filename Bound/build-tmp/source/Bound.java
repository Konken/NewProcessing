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

public class Bound extends PApplet {

final float gravity=9.8f;
Ball[]b=new Ball[500];
Obj o=new Obj(400,400,200);
public void setup() {
	
	for(int i=0;i<b.length;i++){
		b[i]=new Ball(random(width),random(500),10,random(5,10),random(5,10));
	}
}

public void draw() {
	background(0);
	o.drawEllipse();
	for(int i=0;i<b.length;i++){
		b[i].update();
		if(o.collision(b[i].x,b[i].y)){
			b[i].angle=o.getAngle(b[i].x,b[i].y);
			b[i].x=o.x+cos(radians(o.give(b[i].x,b[i].y)))*o.rad/2;
			b[i].y=o.y+sin(radians(o.give(b[i].x,b[i].y)))*o.rad/2;
		}
	}
}

class Ball{
	float x,y,size,spx,spy;
	float nowy;
	float angle=90;
	float xb=1;
	float yb=1;
	Ball(float x,float y,float size,float spy,float spx){
		this.x=x;
		this.y=y;
		this.size=size;
		this.spy=spy;
		spx=0;
		nowy=y;
	}
	public void update(){
		fill(255);
		stroke(255);
		ellipse(x, y,10,10);
		spx=cos(radians(angle))*5*xb;
		spy=sin(radians(angle))*5*yb;
		x+=spx;
		y+=spy;
		if(60<frameCount){
			if(width<x){
				xb*=-0.6f;
				x=width;
			}
			else if(x<0){
				xb*=-0.6f;
				x=0;
			}
			if(height<y){
				yb*=-.6f;
				y=height;
			}
			else if(y<0){
				yb*=-0.6f;
				y=0;
			}
		}
	}
}
class Obj{
	float x,y,rad;
	Obj(float x,float y,float rad){
		this.x=x;
		this.y=y;
		this.rad=rad;
	}
	public void drawEllipse(){
		noFill();
		stroke(255);
		ellipse(x,y,rad,rad);
	}
	public boolean collision(float ballX,float ballY){
		return dist(x,y,ballX,ballY)<rad/2;
	}
	public float getAngle(float ballX,float ballY){
		float temp=give(ballX,ballY);
		if(temp<90){
			//temp+=90;
		}
		else if(temp==90){
			temp=0;
		}
		else{
			//temp-=90;
		}
		return temp;
	}
	public float give(float ballX,float ballY){
		float temp=-degrees(atan2(abs(y-ballY),abs(x-ballX)));
		println("x\u306f"+ballX+" y\u306f"+ballY+" \u89d2\u5ea6\u306f"+temp);
		return temp;
	}
}
  public void settings() { 	fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Bound" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
